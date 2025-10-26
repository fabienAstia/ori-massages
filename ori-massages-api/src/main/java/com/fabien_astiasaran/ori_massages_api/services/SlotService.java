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
import com.fabien_astiasaran.ori_massages_api.repositories.DateRepository;
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

    private DateService dateService;
    private WorkingHoursRepository workingHoursRepository;
    private PrestationRepository prestationRepository;
    private SlotRepository slotRepository;

    public SlotService(DateService dateService, WorkingHoursRepository workingHoursRepository, PrestationRepository prestationRepository, SlotRepository slotRepository) {
        this.dateService = dateService;
        this.workingHoursRepository = workingHoursRepository;
        this.prestationRepository = prestationRepository;
        this.slotRepository = slotRepository;
    }

    public List<SlotResponse> getAvailableSlots(SlotAvailableCreate slotAvailableCreate){
        Prestation prestation = prestationRepository.findById(slotAvailableCreate.prestation().id())
                .orElseThrow(() -> new EntityNotFoundException("Prestation Not Found"));
        List<WorkingHours> workingHours = workingHoursRepository.findAll();
        return createAvailableSlots(slotAvailableCreate, workingHours, prestation);
    }

    private static List<SlotResponse> createAvailableSlots(SlotAvailableCreate slotAvailableCreate, List<WorkingHours> workingHours, Prestation prestation) {
        List<SlotResponse> slots = new ArrayList<>();
        workingHours.forEach(w -> {
            LocalTime startWorkingHour = w.getStartTime();
            LocalTime endWorkingHour = w.getEndTime();

            Integer startWorkingHour_min = startWorkingHour.getHour() * 60 + startWorkingHour.getMinute();
            Integer endWorkingHour_min = endWorkingHour.getHour() * 60 + endWorkingHour.getMinute();
            Integer visibleDuration = slotAvailableCreate.prestation().duration().value();
            Integer realDuration = visibleDuration + slotAvailableCreate.prestation().duration().breakTime();
            Integer timeRange = endWorkingHour_min - startWorkingHour_min;
            Integer numberOfSLots = timeRange / realDuration;

            String formatSlotPattern = "HH:mm";
            DateTimeFormatter formatSlotFormatter = DateTimeFormatter.ofPattern(formatSlotPattern);
            for(int i = 0; i < numberOfSLots; i++){
                Integer startSlotTime = startWorkingHour_min + (i * realDuration);
                SlotResponse slot =  new SlotResponse(
                        null,
                        formatSlotFormatter.format(LocalTime.of(startSlotTime/60, startSlotTime%60)),
                        formatSlotFormatter.format(LocalTime.of((startSlotTime + visibleDuration)/60, (startSlotTime+visibleDuration)%60)),
                        formatSlotFormatter.format(LocalTime.of((startSlotTime + realDuration)/60, (startSlotTime+realDuration)%60)),
                        slotAvailableCreate.date(),
                        WorkingHoursMapper.toResponse(w),
                        PrestationMapper.toResponse(prestation)
                );
                slots.add(slot);
            }
        });
        System.out.println("slots= " + slots);
        return slots;
    }

    public Slot createSlot(AppointmentCreate appointmentCreate, Date date, WorkingHours workingHours, Prestation prestation) {
        Slot slot = new Slot();
        slot.setBeginAt(appointmentCreate.slot().beginAt());
        slot.setEndAt(appointmentCreate.slot().endReal());
        slot.setStatus("booké");
        slot.setDate(date);
        slot.setWorkingHours(workingHours);
        slot.setPrestation(prestation);
        slotRepository.save(slot);
        return slot;
    }
}
