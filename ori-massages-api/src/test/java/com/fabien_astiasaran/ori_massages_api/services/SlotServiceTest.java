package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.*;
import com.fabien_astiasaran.ori_massages_api.entities.*;
import com.fabien_astiasaran.ori_massages_api.exceptions.DateClosedException;
import com.fabien_astiasaran.ori_massages_api.mappers.PrestationMapper;
import com.fabien_astiasaran.ori_massages_api.mappers.WorkingHoursMapper;
import com.fabien_astiasaran.ori_massages_api.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SlotServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PrestationRepository prestationRepository;

    @Mock
    private DateRepository dateRepository;

    @Mock
    private WorkingHoursRepository workingHoursRepository;

    @InjectMocks
    private SlotService slotService;

    @Test
    void getAvailableSlots_throws_DateClosedException(){
        LocalDate date = LocalDate.of(2025, 12, 1);
        DurationCreate duration = new DurationCreate(45, "45 min", 5);
        SlotAvailableCreate slotAvailableCreate = getSlotAvailableCreate(date, duration);
        when(dateRepository.existsByDateAndDateStatus(date, DateStatus.CLOSED)).thenReturn(true);
        assertThrows(
                DateClosedException.class,
                ()-> slotService.getAvailableSlots(slotAvailableCreate)
        );
    }

    @Test
    void getAvailableSlots_throws_EntityNotFoundException(){
        LocalDate date = LocalDate.of(2025, 12, 1);
        DurationCreate duration = new DurationCreate(45, "45 min", 5);
        SlotAvailableCreate slotAvailableCreate = getSlotAvailableCreate(date, duration);
        when(dateRepository.existsByDateAndDateStatus(date, DateStatus.CLOSED)).thenReturn(false);
        when(prestationRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(
                EntityNotFoundException.class,
                ()-> slotService.getAvailableSlots(slotAvailableCreate)
        );
    }

    @Test
    void createAvailableSlots_whenNoBookedSlots_returnsFullSlots(){
        LocalDate date = LocalDate.of(2025, 12, 1);
        DurationCreate durationCreate = new DurationCreate(45, "45 min", 5);
        SlotAvailableCreate slotAvailableCreate = getSlotAvailableCreate(date, durationCreate);

        WorkingHours morningWorkingHours = getWorkingHours(LocalTime.of(9, 00), LocalTime.of(12, 00));
        Duration duration = getFiftyMinDuration();
        Prestation prestation = getPrestation(duration);

        List<SlotResponse> slots = getCreatedSlotResponses(date, morningWorkingHours, prestation);

        when(appointmentRepository.findBookedSlots()).thenReturn(Set.of());
        assertEquals(slots, slotService.createAvailableSlots(slotAvailableCreate, List.of(morningWorkingHours), prestation));
    }

    @Test
    void createAvailableSlots_withBookedSlots_inTheMiddle(){
        LocalDate localDate = LocalDate.of(2025, 12, 1);
        DurationCreate durationCreate = new DurationCreate(45, "45 min", 5);
        SlotAvailableCreate slotAvailableCreate = getSlotAvailableCreate(localDate, durationCreate);

        WorkingHours morningWorkingHours = getWorkingHours(LocalTime.of(9, 00), LocalTime.of(12, 00));
        Duration duration = getFiftyMinDuration();
        Prestation prestation = getPrestation(duration);

        List<SlotResponse> slots = getFilteredSlotResponses_middle(localDate, morningWorkingHours, prestation);
        Set<Slot> bookedSlots = Set.of(getSlot(localDate, LocalTime.of(9, 55),LocalTime.of(10, 45)));
        when(appointmentRepository.findBookedSlots()).thenReturn(bookedSlots);

        assertEquals(slots, slotService.createAvailableSlots(slotAvailableCreate, List.of(morningWorkingHours), prestation));
    }

    @Test
    void createAvailableSlots_withBookedSlots_inTheBeginning(){
        LocalDate localDate = LocalDate.of(2025, 12, 1);
        DurationCreate durationCreate = new DurationCreate(45, "45 min", 5);
        SlotAvailableCreate slotAvailableCreate = getSlotAvailableCreate(localDate, durationCreate);

        WorkingHours morningWorkingHours = getWorkingHours(LocalTime.of(9, 00), LocalTime.of(12, 00));
        Duration duration = getFiftyMinDuration();
        Prestation prestation = getPrestation(duration);

        List<SlotResponse> slots = getFilteredSlotResponses_beginning(localDate, morningWorkingHours, prestation);
        Set<Slot> bookedSlots = Set.of(getSlot(localDate, LocalTime.of(9, 00),LocalTime.of(9, 50)));
        when(appointmentRepository.findBookedSlots()).thenReturn(bookedSlots);

        assertEquals(slots, slotService.createAvailableSlots(slotAvailableCreate, List.of(morningWorkingHours), prestation));
    }

    @Test
    void createAvailableSlots_withBookedSlots_inTheEnd(){
        LocalDate localDate = LocalDate.of(2025, 12, 1);
        DurationCreate durationCreate = new DurationCreate( 45, "45 min", 5);
        SlotAvailableCreate slotAvailableCreate = getSlotAvailableCreate(localDate, durationCreate);

        WorkingHours morningWorkingHours = getWorkingHours(LocalTime.of(9, 00), LocalTime.of(12, 00));
        Duration duration = getFiftyMinDuration();
        Prestation prestation = getPrestation(duration);

        List<SlotResponse> slots = getFilteredSlotResponses_end(localDate, morningWorkingHours, prestation);
        Set<Slot> bookedSlots = Set.of(getSlot(localDate, LocalTime.of(11, 00),LocalTime.of(12, 00)));
        when(appointmentRepository.findBookedSlots()).thenReturn(bookedSlots);

        assertEquals(slots, slotService.createAvailableSlots(slotAvailableCreate, List.of(morningWorkingHours), prestation));
    }

    @Test
    void createAvailableSlots_withBookedSlots_spaceFor2Slots(){
        LocalDate localDate = LocalDate.of(2025, 12, 1);
        DurationCreate durationCreate = new DurationCreate( 45, "45 min", 5);
        SlotAvailableCreate slotAvailableCreate = getSlotAvailableCreate(localDate, durationCreate);

        WorkingHours morningWorkingHours = getWorkingHours(LocalTime.of(9, 00), LocalTime.of(13, 00));
        Duration duration = getFiftyMinDuration();
        Prestation prestation = getPrestation(duration);

        List<SlotResponse> slots = getFilteredSlotResponses_2slots(localDate, morningWorkingHours, prestation);
        Set<Slot> bookedSlots = Set.of(getSlot(localDate, LocalTime.of(9, 00),LocalTime.of(10, 00)),
                getSlot(localDate, LocalTime.of(12, 00), LocalTime.of(12, 50)));
        when(appointmentRepository.findBookedSlots()).thenReturn(bookedSlots);

        assertEquals(slots, slotService.createAvailableSlots(slotAvailableCreate, List.of(morningWorkingHours), prestation));
    }

    private static Slot getSlot(LocalDate localDate, LocalTime startTime, LocalTime endTime) {
        Slot bookedSlot = new Slot();
        bookedSlot.setBeginAt(startTime);
        bookedSlot.setEndAt(endTime);
        Date date = new Date();
        date.setDate(localDate);
        bookedSlot.setDate(date);
        return bookedSlot;
    }

    private static Duration getFiftyMinDuration() {
        Duration duration = new Duration();
        duration.setBreakTime(5);
        duration.setValue(45);
        return duration;
    }

    private static WorkingHours getWorkingHours(LocalTime startTime, LocalTime endTime) {
        WorkingHours morningWorkingHours = new WorkingHours();
        morningWorkingHours.setStartTime(startTime);
        morningWorkingHours.setEndTime(endTime);
        return morningWorkingHours;
    }

    private static Prestation getPrestation(Duration duration) {
        Prestation prestation = new Prestation();
        prestation.setActive(true);
        prestation.setDuration(duration);
        return prestation;
    }

    private static List<SlotResponse> getCreatedSlotResponses(LocalDate date, WorkingHours morningWorkingHours, Prestation prestation) {
        List<SlotResponse> slots = List.of(
                new SlotResponse(
                        null,
                        "09:00",
                        "09:45",
                        "09:50",
                        date,
                        WorkingHoursMapper.toResponse(morningWorkingHours),
                        PrestationMapper.toResponse(prestation)
                ),
                new SlotResponse(
                        null,
                        "09:50",
                        "10:35",
                        "10:40",
                        date,
                        WorkingHoursMapper.toResponse(morningWorkingHours),
                        PrestationMapper.toResponse(prestation)
                ),
                new SlotResponse(
                        null,
                        "10:40",
                        "11:25",
                        "11:30",
                        date,
                        WorkingHoursMapper.toResponse(morningWorkingHours),
                        PrestationMapper.toResponse(prestation)
                )
        );
        return slots;
    }

    private static List<SlotResponse> getFilteredSlotResponses_middle(LocalDate date, WorkingHours morningWorkingHours, Prestation prestation) {
        List<SlotResponse> slots = List.of(
                new SlotResponse(
                        null,
                        "09:00",
                        "09:45",
                        "09:50",
                        date,
                        WorkingHoursMapper.toResponse(morningWorkingHours),
                        PrestationMapper.toResponse(prestation)
                ),
                new SlotResponse(
                        null,
                        "10:45",
                        "11:30",
                        "11:35",
                        date,
                        WorkingHoursMapper.toResponse(morningWorkingHours),
                        PrestationMapper.toResponse(prestation)
                )
        );
        return slots;
    }

    private static List<SlotResponse> getFilteredSlotResponses_beginning(LocalDate date, WorkingHours morningWorkingHours, Prestation prestation) {
        List<SlotResponse> slots = List.of(
                new SlotResponse(
                        null,
                        "09:50",
                        "10:35",
                        "10:40",
                        date,
                        WorkingHoursMapper.toResponse(morningWorkingHours),
                        PrestationMapper.toResponse(prestation)
                ),
                new SlotResponse(
                        null,
                        "10:40",
                        "11:25",
                        "11:30",
                        date,
                        WorkingHoursMapper.toResponse(morningWorkingHours),
                        PrestationMapper.toResponse(prestation)
                )
        );
        return slots;
    }

    private static List<SlotResponse> getFilteredSlotResponses_end(LocalDate date, WorkingHours morningWorkingHours, Prestation prestation) {
        List<SlotResponse> slots = List.of(
                new SlotResponse(
                        null,
                        "09:00",
                        "09:45",
                        "09:50",
                        date,
                        WorkingHoursMapper.toResponse(morningWorkingHours),
                        PrestationMapper.toResponse(prestation)
                ),
                new SlotResponse(
                        null,
                        "09:50",
                        "10:35",
                        "10:40",
                        date,
                        WorkingHoursMapper.toResponse(morningWorkingHours),
                        PrestationMapper.toResponse(prestation)
                )
        );
        return slots;
    }

    private static List<SlotResponse> getFilteredSlotResponses_2slots(LocalDate date, WorkingHours morningWorkingHours, Prestation prestation) {
        List<SlotResponse> slots = List.of(
                new SlotResponse(
                        null,
                        "10:00",
                        "10:45",
                        "10:50",
                        date,
                        WorkingHoursMapper.toResponse(morningWorkingHours),
                        PrestationMapper.toResponse(prestation)
                ),
                new SlotResponse(
                        null,
                        "10:50",
                        "11:35",
                        "11:40",
                        date,
                        WorkingHoursMapper.toResponse(morningWorkingHours),
                        PrestationMapper.toResponse(prestation)
                )
        );
        return slots;
    }

    private static SlotAvailableCreate getSlotAvailableCreate(LocalDate date, DurationCreate durationCreate) {
        SlotAvailableCreate slotAvailableCreate = new SlotAvailableCreate(
                date,
                new PrestationCreate(
                        1L,
                        "name",
                        "description",
                        "60",
                        durationCreate
                )
        );
        return slotAvailableCreate;
    }

}