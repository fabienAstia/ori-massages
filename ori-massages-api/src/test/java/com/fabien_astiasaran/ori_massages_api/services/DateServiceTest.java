package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.AllDateResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.AvailabilityAction;
import com.fabien_astiasaran.ori_massages_api.dtos.DateSetAvailabilityRequest;
import com.fabien_astiasaran.ori_massages_api.entities.Date;
import com.fabien_astiasaran.ori_massages_api.entities.DateStatus;
import com.fabien_astiasaran.ori_massages_api.repositories.DateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fabien_astiasaran.ori_massages_api.services.DateService.toLocalDateSet;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DateServiceTest {

    @Mock
    private DateRepository dateRepository;

    @InjectMocks
    private DateService dateService;

    @Test
    void updateAvailability_makeAvailable(){
        LocalDate firstDay = LocalDate.of(2000, 12, 1);
        LocalDate lastDay = LocalDate.of(2000, 12, 5);
        DateSetAvailabilityRequest request = new DateSetAvailabilityRequest(firstDay, lastDay, AvailabilityAction.MAKE_AVAILABLE);

        List<Date> openDateEntities = new ArrayList<>(List.of( getOpenPreviousDate(), getOpenRangeDate(), getOpenAfterDate()));
        List<Date> closedDateEntities = new ArrayList<>(List.of(getClosedPreviousDate(), getClosedRangeDate(), getClosedAfterDate()));
        List<Date> bookedDateEntities = new ArrayList<>(List.of(getBookedPreviousDate(), getBookedRangesDate(), getBookedAfterDate()));

        when(dateRepository.findAllByDateStatus(DateStatus.OPEN)).thenReturn(openDateEntities);
        when(dateRepository.findAllByDateStatus(DateStatus.CLOSED)).thenReturn(closedDateEntities);
        when(dateRepository.findAllByDateStatus(DateStatus.BOOKED)).thenReturn(bookedDateEntities);

        when(dateRepository.saveAll(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Set<LocalDate> expectedOpen = toLocalDateSet(openDateEntities);
        Set<LocalDate> expectedClosed = toLocalDateSet(closedDateEntities);
        Set<LocalDate> expectedBooked = toLocalDateSet(bookedDateEntities);

        Set<LocalDate> dateRange = firstDay.datesUntil(lastDay.plusDays(1L)).collect(Collectors.toSet());
        Set<LocalDate> dateToChangeStatus = dateRange.stream().filter(d -> expectedClosed.contains(d)).collect(Collectors.toSet());
        Set<LocalDate> newlyCreatedDates = dateRange.stream().filter(d -> !expectedOpen.contains(d) && !expectedClosed.contains(d) && !expectedBooked.contains(d)).collect(Collectors.toSet());
        expectedClosed.removeAll(dateToChangeStatus);
        expectedOpen.addAll(dateToChangeStatus);
        expectedOpen.addAll(newlyCreatedDates);

        AllDateResponse response = new AllDateResponse(
                expectedOpen,
                expectedClosed,
                expectedBooked
        );

        Assertions.assertEquals(response, dateService.updateAvailability(request));
    }

    @Test
    void updateAvailability_makeUnvailable(){
        LocalDate firstDay = LocalDate.of(2000, 12, 1);
        LocalDate lastDay = LocalDate.of(2000, 12, 5);
        DateSetAvailabilityRequest request = new DateSetAvailabilityRequest(firstDay, lastDay, AvailabilityAction.MAKE_UNAVAILABLE);

        List<Date> openDateEntities = new ArrayList<>(List.of( getOpenPreviousDate(), getOpenRangeDate(), getOpenAfterDate()));
        List<Date> closedDateEntities = new ArrayList<>(List.of(getClosedPreviousDate(), getClosedRangeDate(), getClosedAfterDate()));
        List<Date> bookedDateEntities = new ArrayList<>(List.of(getBookedPreviousDate(), getBookedRangesDate(), getBookedAfterDate()));

        when(dateRepository.findAllByDateStatus(DateStatus.OPEN)).thenReturn(openDateEntities);
        when(dateRepository.findAllByDateStatus(DateStatus.CLOSED)).thenReturn(closedDateEntities);
        when(dateRepository.findAllByDateStatus(DateStatus.BOOKED)).thenReturn(bookedDateEntities);

        when(dateRepository.saveAll(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Set<LocalDate> expectedOpen = toLocalDateSet(openDateEntities);
        Set<LocalDate> expectedClosed = toLocalDateSet(closedDateEntities);
        Set<LocalDate> expectedBooked = toLocalDateSet(bookedDateEntities);

        Set<LocalDate> dateRange = firstDay.datesUntil(lastDay.plusDays(1L)).collect(Collectors.toSet());
        Set<LocalDate> dateToChangeStatus = dateRange.stream().filter(d -> expectedOpen.contains(d)).collect(Collectors.toSet());
        Set<LocalDate> newlyCreatedDates = dateRange.stream().filter(d -> !expectedOpen.contains(d) && !expectedClosed.contains(d) && !expectedBooked.contains(d)).collect(Collectors.toSet());
        expectedOpen.removeAll(dateToChangeStatus);
        expectedClosed.addAll(dateToChangeStatus);
        expectedClosed.addAll(newlyCreatedDates);

        AllDateResponse response = new AllDateResponse(
                expectedOpen,
                expectedClosed,
                expectedBooked
        );

        Assertions.assertEquals(response, dateService.updateAvailability(request));
    }

    private static Date getOpenAfterDate() {
        Date openAfterDate = new Date();
        openAfterDate.setDate(LocalDate.of(2012, 12, 5));
        openAfterDate.setDateStatus(DateStatus.OPEN);
        return openAfterDate;
    }

    private static Date getOpenRangeDate() {
        Date openRangeDate = new Date();
        openRangeDate.setDate(LocalDate.of(2000, 12, 2));
        openRangeDate.setDateStatus(DateStatus.OPEN);
        return openRangeDate;
    }

    private static Date getOpenPreviousDate() {
        Date openPreviousDate = new Date();
        openPreviousDate.setDate(LocalDate.of(1995, 01, 01));
        openPreviousDate.setDateStatus(DateStatus.OPEN);
        return openPreviousDate;
    }

    private static Date getClosedPreviousDate() {
        Date closedPreviousDate = new Date();
        closedPreviousDate.setDate(LocalDate.of(1995, 02, 01));
        closedPreviousDate.setDateStatus(DateStatus.CLOSED);
        return closedPreviousDate;
    }

    private static Date getClosedRangeDate() {
        Date closedRangeDate = new Date();
        closedRangeDate.setDate(LocalDate.of(2000, 12, 03));
        closedRangeDate.setDateStatus(DateStatus.CLOSED);
        return closedRangeDate;
    }

    private static Date getClosedAfterDate() {
        Date closedPreviousDate = new Date();
        closedPreviousDate.setDate(LocalDate.of(2005, 02, 01));
        closedPreviousDate.setDateStatus(DateStatus.CLOSED);
        return closedPreviousDate;
    }

    private static Date getBookedPreviousDate() {
        Date bookedPreviousDate = new Date();
        bookedPreviousDate.setDate(LocalDate.of(1990, 02, 01));
        bookedPreviousDate.setDateStatus(DateStatus.BOOKED);
        return bookedPreviousDate;
    }

    private static Date getBookedRangesDate() {
        Date bookedRangeDate = new Date();
        bookedRangeDate.setDate(LocalDate.of(2000, 12, 4));
        bookedRangeDate.setDateStatus(DateStatus.BOOKED);
        return bookedRangeDate;
    }

    private static Date getBookedAfterDate() {
        Date bookedfterDate = new Date();
        bookedfterDate.setDate(LocalDate.of(2100, 02, 01));
        bookedfterDate.setDateStatus(DateStatus.BOOKED);
        return bookedfterDate;
    }
}