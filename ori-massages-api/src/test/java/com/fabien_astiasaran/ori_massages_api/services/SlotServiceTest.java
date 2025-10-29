package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.DurationResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.PrestationResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.SlotResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.WorkingHoursResponse;
import com.fabien_astiasaran.ori_massages_api.entities.Date;
import com.fabien_astiasaran.ori_massages_api.entities.Slot;
import com.fabien_astiasaran.ori_massages_api.repositories.SlotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.fabien_astiasaran.ori_massages_api.services.SlotService.localTimeToString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SlotServiceTest {

    @Mock
    private SlotRepository repository;

    @InjectMocks
    private SlotService slotService;

    @Test
    void filterBookedSlots_whenSlotBeginsInsideExisting1_shouldFilterOut(){
        //GIVEN
        DurationResponse duration = new DurationResponse(1L, 45, "massage court", 5);
        SlotResponse slotA = getSlotA(duration);
        SlotResponse slotB = getSlotB(duration);
        List<SlotResponse> newCreatedSlots = List.of(slotA, slotB);

        Date date = new Date();
        date.setDate(LocalDate.of(2025, 10, 25));
        Slot slotAlike = getSlotAlike(date);
        Slot slotNotALike = getSlotNotALike(date);
        List<Slot> existingSlots = List.of(slotAlike, slotNotALike);

        //WHEN
        when(repository.findAll()).thenReturn(existingSlots);

        //THEN
        Assertions.assertEquals(slotService.filterBookedSlots(newCreatedSlots), List.of(slotA));
    }

    @Test
    void filterBookedSlots_whenSlotBeginsInsideExisting2_shouldFilterOut(){
        //GIVEN
        DurationResponse duration = new DurationResponse(1L, 45, "massage court", 5);
        SlotResponse slotA = getSlotA(duration);
        SlotResponse slotB = getSlotB(duration);
        SlotResponse slotC = getSlotC(duration);
        List<SlotResponse> newCreatedSlots = List.of(slotA, slotB, slotC);

        Date date = new Date();
        date.setDate(LocalDate.of(2025, 10, 25));
        List<Slot> existingSlots = List.of(getSlotAllAfternoon(date));

        //WHEN
        when(repository.findAll()).thenReturn(existingSlots);

        //THEN
        Assertions.assertEquals(slotService.filterBookedSlots(newCreatedSlots), List.of(slotA, slotB));
    }

    @Test
    void filterBookedSlots_whenSlotEndsInsideExisting_shouldFilterOut(){
        //GIVEN
        DurationResponse duration = new DurationResponse(2L, 45, "massage court", 5);
        SlotResponse slotA = getSlotA(duration);
        SlotResponse slotB = getSlotB(duration);
        SlotResponse slotD = getSlotD(duration);
        List<SlotResponse> newCreatedSlots = List.of(slotA, slotB, slotD);

        Date date = new Date();
        date.setDate(LocalDate.of(2025, 10, 25));
        List<Slot> existingSlots = List.of(getSlotAllAfternoon(date));

        //WHEN
        when(repository.findAll()).thenReturn(existingSlots);

        //THEN
        Assertions.assertEquals(slotService.filterBookedSlots(newCreatedSlots), List.of(slotA, slotB));
    }

    @Test
    void filterBookedSlots_whenSlotFullyOverlapsExisting_shouldFilterOut(){
        //GIVEN
        DurationResponse duration = new DurationResponse(2L, 45, "massage court", 5);
        SlotResponse slotA = getSlotA(duration);
        SlotResponse slotB = getSlotB(duration);
        SlotResponse slotE = getSlotE(duration);
        List<SlotResponse> newCreatedSlots = List.of(slotA, slotB, slotE);

        Date date = new Date();
        date.setDate(LocalDate.of(2025, 10, 25));
        List<Slot> existingSlots = List.of(getSlotSmallAfternoon(date));

        //WHEN
        when(repository.findAll()).thenReturn(existingSlots);

        //THEN
        Assertions.assertEquals(slotService.filterBookedSlots(newCreatedSlots), List.of(slotA, slotB));
    }

    private static Slot getSlotNotALike(Date date) {
        Slot slotNotALike = new Slot();
        slotNotALike.setBeginAt(LocalTime.of(15, 35));
        slotNotALike.setEndAt(LocalTime.of(16, 10));
        slotNotALike.setDate(date);
        return slotNotALike;
    }

    private static Slot getSlotAlike(Date date) {
        Slot slotAlike = new Slot();
        slotAlike.setBeginAt(LocalTime.of(11, 20));
        slotAlike.setEndAt(LocalTime.of(12, 10));
        slotAlike.setDate(date);
        return slotAlike;
    }

    private static Slot getSlotAllAfternoon(Date date) {
        Slot slotLongTime = new Slot();
        slotLongTime.setBeginAt(LocalTime.of(13, 20));
        slotLongTime.setEndAt(LocalTime.of(17, 55));
        slotLongTime.setDate(date);
        return slotLongTime;
    }

    private static Slot getSlotSmallAfternoon(Date date) {
        Slot slotLongTime = new Slot();
        slotLongTime.setBeginAt(LocalTime.of(15, 20));
        slotLongTime.setEndAt(LocalTime.of(15, 35));
        slotLongTime.setDate(date);
        return slotLongTime;
    }

    private static SlotResponse getSlotA(DurationResponse duration) {
        return new SlotResponse(
                1L,
                localTimeToString(LocalTime.of(10, 30)),
                localTimeToString(LocalTime.of(11, 15)),
                localTimeToString(LocalTime.of(10, 40)),
                LocalDate.of(2025, 10, 25),
                new WorkingHoursResponse(1L, null, null, null),
                new PrestationResponse(1L, null, null, null, true, null, duration)
        );
    }

    private static SlotResponse getSlotB(DurationResponse duration) {
        return new SlotResponse(
                2L,
                localTimeToString(LocalTime.of(11, 20)),
                localTimeToString(LocalTime.of(12, 05)),
                localTimeToString(LocalTime.of(12, 10)),
                LocalDate.of(2025, 10, 25),
                new WorkingHoursResponse(1L, null, null, null),
                new PrestationResponse(1L, null, null, null, true, null, duration)
        );
    }

    private static SlotResponse getSlotC(DurationResponse duration) {
        return new SlotResponse(
                2L,
                localTimeToString(LocalTime.of(14, 20)),
                localTimeToString(LocalTime.of(15, 05)),
                localTimeToString(LocalTime.of(15, 10)),
                LocalDate.of(2025, 10, 25),
                new WorkingHoursResponse(1L, null, null, null),
                new PrestationResponse(1L, null, null, null, true, null, duration)
        );
    }

    private static SlotResponse getSlotD(DurationResponse duration) {
        return new SlotResponse(
                2L,
                localTimeToString(LocalTime.of(11, 00)),
                localTimeToString(LocalTime.of(14, 05)),
                localTimeToString(LocalTime.of(14, 10)),
                LocalDate.of(2025, 10, 25),
                new WorkingHoursResponse(1L, null, null, null),
                new PrestationResponse(1L, null, null, null, true, null, duration)
        );
    }

    private static SlotResponse getSlotE(DurationResponse duration) {
        return new SlotResponse(
                2L,
                localTimeToString(LocalTime.of(15, 00)),
                localTimeToString(LocalTime.of(16, 00)),
                localTimeToString(LocalTime.of(16, 10)),
                LocalDate.of(2025, 10, 25),
                new WorkingHoursResponse(1L, null, null, null),
                new PrestationResponse(1L, null, null, null, true, null, duration)
        );
    }

}