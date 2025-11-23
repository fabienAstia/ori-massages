package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.*;
import com.fabien_astiasaran.ori_massages_api.entities.Date;
import com.fabien_astiasaran.ori_massages_api.entities.DateStatus;
import com.fabien_astiasaran.ori_massages_api.repositories.DateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DateService {

    private final DateRepository dateRepository;

    public DateService(DateRepository dateRepository) {
        this.dateRepository = dateRepository;
    }

    public Date findOrCreateDate(SlotCreate slotCreate) {
        return dateRepository.findByDate(slotCreate.date())
                .orElseGet(() -> {
                    Date newDate = new Date();
                    newDate.setDate(slotCreate.date());
                    newDate.setDateStatus(DateStatus.BOOKED);
                    return dateRepository.save(newDate);
                });
    }

    public DateSetResponse getBookedDates(){
        Set<LocalDate> bookedDates = dateRepository.findAllByDateStatus(DateStatus.BOOKED).stream().map(d -> d.getDate()).collect(Collectors.toSet());
        return new DateSetResponse(bookedDates);
    }

    public DateSetResponse getClosedDates(){
        Set<LocalDate> closedDates = dateRepository.findAllByDateStatus(DateStatus.CLOSED).stream().map(d -> d.getDate()).collect(Collectors.toSet());
        return new DateSetResponse(closedDates);
    }

    public AllDateResponse getAllDates() {
        List<Date> dates = dateRepository.findAll();
        Set<LocalDate> bookedLocalDates = filterLocalDatesByStatus(dates, DateStatus.BOOKED);
        Set<LocalDate> existingOpenLocalDates = filterLocalDatesByStatus(dates, DateStatus.OPEN);
        Set<LocalDate> existingClosedLocalDates = filterLocalDatesByStatus(dates, DateStatus.CLOSED);
        return new AllDateResponse(
                existingOpenLocalDates,
                existingClosedLocalDates,
                bookedLocalDates
        );
    }

    private static Set<LocalDate> filterLocalDatesByStatus(List<Date> dates, DateStatus status) {
        return dates.stream().filter(d -> d.getDateStatus() == status).map(Date::getDate).collect(Collectors.toSet());
    }

    public AllDateResponse updateAvailability(DateSetAvailabilityRequest request) {
        LocalDate firstDay = request.firstDay();
        LocalDate lastDay = request.lastDay();
        Set<LocalDate> dateRange = new HashSet<>(firstDay.datesUntil(lastDay.plusDays(1L)).toList());

        List<Date> openDateEntities = dateRepository.findAllByDateStatus(DateStatus.OPEN);
        Set<LocalDate> openDateSet = toLocalDateSet(openDateEntities);
        List<Date> closedDateEntities = dateRepository.findAllByDateStatus(DateStatus.CLOSED);
        Set<LocalDate> closedDateSet = toLocalDateSet(closedDateEntities);
        List<Date> bookedDateEntities = dateRepository.findAllByDateStatus(DateStatus.BOOKED);
        Set<LocalDate> bookedDateSet = toLocalDateSet(bookedDateEntities);

        if(request.availability() == AvailabilityAction.MAKE_AVAILABLE){
            updateRangeStatus(dateRange, DateStatus.OPEN, closedDateEntities, openDateSet, closedDateSet, bookedDateSet);
        } else if (request.availability() == AvailabilityAction.MAKE_UNAVAILABLE) {
            updateRangeStatus(dateRange, DateStatus.CLOSED, openDateEntities, closedDateSet, openDateSet, bookedDateSet);
        }
        return new AllDateResponse(
                openDateSet,
                closedDateSet,
                bookedDateSet
        );
    }

    public static Set<LocalDate> toLocalDateSet(List<Date> entities) {
        return entities.stream().map(d -> d.getDate()).collect(Collectors.toSet());
    }

    private void updateRangeStatus(Set<LocalDate> dateRange, DateStatus targetStatus, List<Date> entitiesStatusToUpdate, Set<LocalDate> targetStatusDateSet, Set<LocalDate> previousStatusDate, Set<LocalDate> bookedDateSet) {
        List<Date> updatedEntities = updateExistingDatesStatus(entitiesStatusToUpdate, dateRange, targetStatus);
        Set<LocalDate> updatedDateSet = toLocalDateSet(updatedEntities);
        previousStatusDate.removeAll(updatedDateSet);
        targetStatusDateSet.addAll(updatedDateSet);
        Set<LocalDate> datesToCreate = getMissingDates(dateRange, targetStatusDateSet, updatedDateSet, bookedDateSet);
        Set<LocalDate> createdDateSet = toLocalDateSet(dateRepository.saveAll(createMissingDateEntities(datesToCreate, targetStatus)));
        targetStatusDateSet.addAll(createdDateSet);
    }

    private static Set<LocalDate> getMissingDates(Set<LocalDate> dateRange, Set<LocalDate> existingTargetStatusDates, Set<LocalDate> updatedDateSet, Set<LocalDate> bookedDateSet) {
        Set<LocalDate> excludedDates = new HashSet<>();
        excludedDates.addAll(existingTargetStatusDates);
        excludedDates.addAll(updatedDateSet);
        excludedDates.addAll(bookedDateSet);

        return dateRange.stream()
                .filter(date -> !excludedDates.contains(date))
                .collect(Collectors.toSet());
    }

    private List<Date> updateExistingDatesStatus(List<Date> existingEntities, Set<LocalDate> dateRange, DateStatus status) {
        List<Date> entitiesInRange = existingEntities.stream()
                .filter(d -> dateRange.contains(d.getDate()))
                .toList();
        entitiesInRange.forEach(date -> date.setDateStatus(status));
        return dateRepository.saveAll(entitiesInRange);
    }

    private List<Date> createMissingDateEntities(Set<LocalDate> missingDateSet, DateStatus status) {
        List<Date> newEntities = new ArrayList<>();
        missingDateSet.forEach(localDate -> {
            Date date = new Date();
            date.setDate(localDate);
            date.setDateStatus(status);
            newEntities.add(date);
        });
        return newEntities;
    }
}
