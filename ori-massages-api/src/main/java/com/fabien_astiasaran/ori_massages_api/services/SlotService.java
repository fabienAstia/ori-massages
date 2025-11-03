package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.AppointmentCreate;
import com.fabien_astiasaran.ori_massages_api.dtos.SlotAvailableCreate;
import com.fabien_astiasaran.ori_massages_api.dtos.SlotResponse;
import com.fabien_astiasaran.ori_massages_api.entities.Date;
import com.fabien_astiasaran.ori_massages_api.entities.Prestation;
import com.fabien_astiasaran.ori_massages_api.entities.Slot;
import com.fabien_astiasaran.ori_massages_api.entities.WorkingHours;
import com.fabien_astiasaran.ori_massages_api.mappers.PrestationMapper;
import com.fabien_astiasaran.ori_massages_api.mappers.WorkingHoursMapper;
import com.fabien_astiasaran.ori_massages_api.repositories.PrestationRepository;
import com.fabien_astiasaran.ori_massages_api.repositories.SlotRepository;
import com.fabien_astiasaran.ori_massages_api.repositories.WorkingHoursRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class SlotService {

    private WorkingHoursRepository workingHoursRepository;
    private PrestationRepository prestationRepository;
    private SlotRepository slotRepository;

    public SlotService(WorkingHoursRepository workingHoursRepository, PrestationRepository prestationRepository, SlotRepository slotRepository) {
        this.workingHoursRepository = workingHoursRepository;
        this.prestationRepository = prestationRepository;
        this.slotRepository = slotRepository;
    }

    public List<SlotResponse> getAvailableSlots(SlotAvailableCreate slotAvailableCreate){
        Prestation prestation = prestationRepository.findById(slotAvailableCreate.prestation().id())
                .orElseThrow(() -> new EntityNotFoundException("Prestation Not Found"));
        List<WorkingHours> workingHours = workingHoursRepository.findAll();
        List<SlotResponse> createdSlots = createAvailableSlots(slotAvailableCreate, workingHours, prestation);
        return filterBookedSlots(createdSlots);
    }

    private List<SlotResponse> createAvailableSlots(SlotAvailableCreate slotAvailableCreate, List<WorkingHours> workingHours, Prestation prestation) {
        List<SlotResponse> slots = new ArrayList<>();
        workingHours.forEach(w -> {
            LocalTime startWorkingHour = w.getStartTime();
            LocalTime endWorkingHour = w.getEndTime();

            Integer startWorkingHourInMin = startWorkingHour.getHour() * 60 + startWorkingHour.getMinute();
            Integer endWorkingHourInMin = endWorkingHour.getHour() * 60 + endWorkingHour.getMinute();
            Integer visibleDuration = slotAvailableCreate.prestation().duration().value();
            Integer realDuration = visibleDuration + slotAvailableCreate.prestation().duration().breakTime();
            Integer timeRange = endWorkingHourInMin - startWorkingHourInMin;
            Integer numberOfSLots = timeRange / realDuration;

            for(int i = 0; i < numberOfSLots; i++) {
                Integer startSlotTime = startWorkingHourInMin + (i * realDuration);
                SlotResponse slotResp =  new SlotResponse(
                        null,
                        localTimeToString(startSlotTime / 60, startSlotTime % 60),
                        localTimeToString((startSlotTime + visibleDuration) /60, (startSlotTime + visibleDuration) % 60),
                        localTimeToString((startSlotTime + realDuration) / 60, (startSlotTime + realDuration) % 60),
                        slotAvailableCreate.date(),
                        WorkingHoursMapper.toResponse(w),
                        PrestationMapper.toResponse(prestation)
                );
                slots.add(slotResp);
            }
        });
        return slots;
    }

    public List<SlotResponse> filterBookedSlots(List<SlotResponse> slots){
        List<Slot> existingSlots = slotRepository.findAll();
        List<SlotResponse> filteredSlots = new ArrayList<>();
        slots.forEach(slot -> {
            Boolean cannotBook = false;
            for(Slot existingSlot : existingSlots){
                if(beginsInsideExistingSlot(slot, existingSlot)
                        || endsInsideExistingSlot(slot, existingSlot)
                        || fullyOverlapsExistingSlot(slot, existingSlot))
                {
                    cannotBook = true;
                }
            }
            if(!cannotBook){
                filteredSlots.add(slot);
            }
        });
        return filteredSlots;
    }

    private static boolean beginsInsideExistingSlot(SlotResponse slot, Slot existingSlot) {
        LocalTime beginAt = existingSlot.getBeginAt();
        LocalTime endAt = existingSlot.getEndAt();
        return (LocalTime.parse(slot.beginAt()).isAfter(beginAt) || LocalTime.parse(slot.beginAt()).equals(beginAt))
                && LocalTime.parse(slot.beginAt()).isBefore(endAt)
                && slot.date().equals(existingSlot.getDate().getDate());
    }

    private static boolean endsInsideExistingSlot(SlotResponse slot, Slot existingSlot) {
        LocalTime beginAt = existingSlot.getBeginAt();
        LocalTime endAt = existingSlot.getEndAt();
        return (LocalTime.parse(slot.endReal()).isAfter(beginAt)
                && LocalTime.parse(slot.endReal()).isBefore(endAt)
                && slot.date().equals(existingSlot.getDate().getDate()));
    }

    private static boolean fullyOverlapsExistingSlot(SlotResponse slot, Slot existingSlot) {
        LocalTime beginAt = existingSlot.getBeginAt();
        LocalTime endAt = existingSlot.getEndAt();
        return (LocalTime.parse(slot.beginAt()).isBefore(beginAt)
                && LocalTime.parse(slot.endReal()).isAfter(endAt)
                && slot.date().equals(existingSlot.getDate().getDate()));
    }

    public static String localTimeToString(int hour, int min){
        String formatSlotPattern = "HH:mm";
        DateTimeFormatter formatSlotFormatter = DateTimeFormatter.ofPattern(formatSlotPattern);
        return formatSlotFormatter.format(LocalTime.of(hour, min));
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
