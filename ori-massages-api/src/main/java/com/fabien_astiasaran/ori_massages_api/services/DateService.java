package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.DateBookedResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.DateUnavailableAndBookedResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.DateSetUnavailable;
import com.fabien_astiasaran.ori_massages_api.dtos.SlotCreate;
import com.fabien_astiasaran.ori_massages_api.entities.Date;
import com.fabien_astiasaran.ori_massages_api.mappers.DateMapper;
import com.fabien_astiasaran.ori_massages_api.repositories.DateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DateService {

    private DateRepository dateRepository;

    public DateService(DateRepository dateRepository) {
        this.dateRepository = dateRepository;
    }

    public Date findOrCreateDate(SlotCreate slotCreate) {
        Date date = dateRepository.findByDate(slotCreate.date())
                .orElseGet(() -> {
                    Date newDate = new Date();
                    newDate.setDate(slotCreate.date());
                    newDate.setAvailable(true);
                    return dateRepository.save(newDate);
                });
        return date;
    }

    public DateBookedResponse getDatesAlreadyBooked(){
        List<LocalDate> alreadyBooked = dateRepository.findAllAvailableDates();
        return new DateBookedResponse(alreadyBooked);
    }

    public DateUnavailableAndBookedResponse setUnavailableDates(DateSetUnavailable dateSetUnavailable) {
        LocalDate firstDay = dateSetUnavailable.firstDay();
        LocalDate lastDay = dateSetUnavailable.lastDay();

        List<LocalDate> datesBetween = firstDay
                .datesUntil(lastDay.plusDays(1L))
                .collect(Collectors.toList());

        List<LocalDate> alreadyBooked = dateRepository.findAllAvailableDates();
        List<LocalDate> newUnavailableDates = datesBetween.stream()
                        .filter(date -> !alreadyBooked.contains(date))
                        .collect(Collectors.toUnmodifiableList());
        dateRepository.saveAll(toUnavailableDates(newUnavailableDates));

        alreadyBooked.removeAll(datesBetween);

        return new DateUnavailableAndBookedResponse(
               datesBetween,
               alreadyBooked
        );
    }

    private static List<Date> toUnavailableDates(List<LocalDate> datesBetweenToPersist) {
        List<Date> datesToPersist = new ArrayList<>();
        datesBetweenToPersist.forEach(date -> {
            Date unavailableDate = new Date();
            unavailableDate.setDate(date);
            unavailableDate.setAvailable(false);
            unavailableDate.setComment(null);
            datesToPersist.add(unavailableDate);
        });
        return datesToPersist;
    }

    private List<Date> getDatesBetweenAlreadyBooked(LocalDate firstDay, LocalDate lastDay) {
        List<Date> datesAlreadyBooked = dateRepository
                .findByDateBetween(firstDay, lastDay);
        datesAlreadyBooked.forEach(date -> date.setAvailable(false));
        dateRepository.saveAll(datesAlreadyBooked);
        return datesAlreadyBooked;
    }
}
