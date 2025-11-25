package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.AppointmentCreate;
import com.fabien_astiasaran.ori_massages_api.dtos.SlotAvailableCreate;
import com.fabien_astiasaran.ori_massages_api.dtos.SlotResponse;
import com.fabien_astiasaran.ori_massages_api.entities.*;
import com.fabien_astiasaran.ori_massages_api.entities.Date;
import com.fabien_astiasaran.ori_massages_api.exceptions.DateClosedException;
import com.fabien_astiasaran.ori_massages_api.mappers.PrestationMapper;
import com.fabien_astiasaran.ori_massages_api.mappers.WorkingHoursMapper;
import com.fabien_astiasaran.ori_massages_api.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SlotService {

    private final WorkingHoursRepository workingHoursRepository;
    private final PrestationRepository prestationRepository;
    private final SlotRepository slotRepository;
    private final AppointmentRepository appointmentRepository;
    private final DateRepository dateRepository;

    public SlotService(WorkingHoursRepository workingHoursRepository, PrestationRepository prestationRepository, SlotRepository slotRepository, AppointmentRepository appointmentRepository, DateRepository dateRepository) {
        this.workingHoursRepository = workingHoursRepository;
        this.prestationRepository = prestationRepository;
        this.slotRepository = slotRepository;
        this.appointmentRepository = appointmentRepository;
        this.dateRepository = dateRepository;
    }

    public List<SlotResponse> getAvailableSlots(SlotAvailableCreate slotAvailableCreate){
        boolean isClosed = dateRepository.existsByDateAndDateStatus(slotAvailableCreate.date(), DateStatus.CLOSED);
        if(isClosed){ throw new DateClosedException(slotAvailableCreate.date());}
        Prestation prestation = prestationRepository.findById(slotAvailableCreate.prestation().id())
                .orElseThrow(() -> new EntityNotFoundException("Prestation Not Found"));
        List<WorkingHours> workingHours = workingHoursRepository.findAll();
        return createAvailableSlots(slotAvailableCreate, workingHours, prestation);
    }

    public List<SlotResponse> createAvailableSlots(SlotAvailableCreate slotAvailableCreate, List<WorkingHours> workingHours, Prestation prestation) {
        List<SlotResponse> slots = new ArrayList<>();
        Set<Slot> bookedSlots = appointmentRepository.findBookedSlots().stream()
                .filter(slot -> slot.getDate().getDate().equals(slotAvailableCreate.date()))
                .collect(Collectors.toSet());

        workingHours.forEach(w -> {
            LocalTime startWorkingHour = w.getStartTime();
            LocalTime endWorkingHour = w.getEndTime();

            Integer visibleDuration = slotAvailableCreate.prestation().duration().value();
            Integer realDuration = visibleDuration + slotAvailableCreate.prestation().duration().breakTime();

            LinkedHashSet<Slot> bookedSlotsOfTheWorkingHour = getBookedSlotsForWorkingHour(bookedSlots, startWorkingHour, endWorkingHour);
            List<Window> windows = computeWindows(startWorkingHour, endWorkingHour, realDuration, bookedSlotsOfTheWorkingHour);
            generateSlotsFromWindow(slotAvailableCreate, prestation, w, windows, realDuration, visibleDuration, slots);
        });
        return slots;
    }

    private static void generateSlotsFromWindow(SlotAvailableCreate slotAvailableCreate, Prestation prestation, WorkingHours w, List<Window> windows, Integer realDuration, Integer visibleDuration, List<SlotResponse> slots) {
        for(Window window : windows){
            int startSlot = localTimeToInt(window.start);
            while(localTimeToInt(window.end) - startSlot >= realDuration){
                SlotResponse slotResp =  new SlotResponse(
                        null,
                        localTimeToString(intToLocalTimeWithOffset(startSlot, 0)),
                        localTimeToString(intToLocalTimeWithOffset(startSlot, visibleDuration)),
                        localTimeToString(intToLocalTimeWithOffset(startSlot, realDuration)),
                        slotAvailableCreate.date(),
                        WorkingHoursMapper.toResponse(w),
                        PrestationMapper.toResponse(prestation)
                );
                slots.add(slotResp);
                startSlot += realDuration;
            }
        }
    }

    private static LinkedHashSet<Slot> getBookedSlotsForWorkingHour(Set<Slot> bookedSlots, LocalTime startWorkingHour, LocalTime endWorkingHour) {
        LinkedHashSet<Slot> bookedSlotsOfTheDate = bookedSlots.stream()
                .filter(slot -> !slot.getBeginAt().isBefore(startWorkingHour) && !slot.getEndAt().isAfter(endWorkingHour))
                .sorted(Comparator.comparing(Slot::getBeginAt))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return bookedSlotsOfTheDate;
    }

    private record Window(LocalTime start, LocalTime end){}

    private List<Window> computeWindows(LocalTime startWorkingHour, LocalTime endWorkingHour, int realDuration, LinkedHashSet<Slot> bookedSlots){
        LocalTime cursor = startWorkingHour;
        List<Window> windows = new ArrayList<>();

        for(Slot bookedSlot : bookedSlots){
            if(bookedSlot.getBeginAt().isAfter(cursor)){
                LocalTime windowStart = cursor;
                LocalTime windowEnd = bookedSlot.getBeginAt();

                if(localTimeToInt(windowEnd) - localTimeToInt(windowStart) >= realDuration) {
                    windows.add(new Window(windowStart, windowEnd));
                }
                cursor = bookedSlot.getEndAt();

            }else if(bookedSlot.getEndAt().isAfter(cursor)) {
                cursor = bookedSlot.getEndAt();
            }
        }
        if(cursor.isBefore(endWorkingHour)
                && (localTimeToInt(endWorkingHour) - localTimeToInt(cursor)) >= realDuration) {
            windows.add(new Window(cursor, endWorkingHour));
        }
        return windows;
    }

    private static int localTimeToInt(LocalTime localTime) {
        return localTime.getHour() * 60 + localTime.getMinute();
    }

    private static LocalTime intToLocalTimeWithOffset(int startingTime, int offsetMinutes) {
        return LocalTime.of(
                (startingTime + offsetMinutes) / 60,
                (startingTime + offsetMinutes) % 60);
    }

    public static String localTimeToString(LocalTime localTime){
        String formatSlotPattern = "HH:mm";
        DateTimeFormatter formatSlotFormatter = DateTimeFormatter.ofPattern(formatSlotPattern);
        return formatSlotFormatter.format(LocalTime.of(localTime.getHour(), localTime.getMinute()));
    }

    public Slot createSlot(AppointmentCreate appointmentCreate, Date date, WorkingHours workingHours, Prestation prestation) {
        Slot slot = new Slot();
        slot.setBeginAt(appointmentCreate.slot().beginAt());
        slot.setEndAt(appointmentCreate.slot().endReal());
        slot.setDate(date);
        slot.setWorkingHours(workingHours);
        slot.setPrestation(prestation);
        return slotRepository.save(slot);
    }
}
