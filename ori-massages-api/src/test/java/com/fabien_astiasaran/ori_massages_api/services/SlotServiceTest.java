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
    void filterBookedSlots_Ok(){
        //GIVEN
        DurationResponse duration = new DurationResponse(
                1L, 45, "massage court", 5
        );

        SlotResponse slotA = new SlotResponse(
                1L,
                localTimeToString(LocalTime.of(10, 30)),
                localTimeToString(LocalTime.of(11, 15)),
                localTimeToString(LocalTime.of(10, 40)),
                LocalDate.of(2025, 10, 25),
                new WorkingHoursResponse(1L, null, null, null),
                new PrestationResponse(1L, null, null, null, true, null, duration)
        );

        SlotResponse slotB = new SlotResponse(
                2L,
                localTimeToString(LocalTime.of(11, 20)),
                localTimeToString(LocalTime.of(12, 05)),
                localTimeToString(LocalTime.of(12, 10)),
                LocalDate.of(2025, 10, 25),
                new WorkingHoursResponse(1L, null, null, null),
                new PrestationResponse(1L, null, null, null, true, null, duration)
        );

        List<SlotResponse> newCreatedSlots = List.of(
                slotA, slotB
        );

        Date date = new Date();
        date.setDate(LocalDate.of(2025, 10, 25));

        Slot slotAlike = new Slot();
        slotAlike.setBeginAt(LocalTime.of(11, 20));
        slotAlike.setDate(date);

        Slot slotNotALike = new Slot();
        slotNotALike.setBeginAt(LocalTime.of(15, 35));
        slotNotALike.setDate(date);

        List<Slot> existingSlots = List.of(
                slotAlike, slotNotALike
        );

        //WHEN
        when(repository.findAll()).thenReturn(existingSlots);

        //THEN
        Assertions.assertEquals(slotService.filterBookedSlots(newCreatedSlots), List.of(slotA));
    }

}