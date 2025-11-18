package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.DateBookedResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.DateUnavailableAndBookedResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.DateSetUnavailable;
import com.fabien_astiasaran.ori_massages_api.dtos.SlotCreate;
import com.fabien_astiasaran.ori_massages_api.entities.Date;
import com.fabien_astiasaran.ori_massages_api.repositories.DateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.fabien_astiasaran.ori_massages_api.mappers.DateMapper.toLocalDate;

@Service
public class DateService {

    private DateRepository dateRepository;

    public DateService(DateRepository dateRepository) {
        this.dateRepository = dateRepository;
    }

    public Date findOrCreateDate(SlotCreate slotCreate) {
        return dateRepository.findByDate(slotCreate.date())
                .orElseGet(() -> {
                    Date newDate = new Date();
                    newDate.setDate(slotCreate.date());
                    newDate.setAvailable(true);
                    return dateRepository.save(newDate);
                });
    }

    public DateBookedResponse getDatesAlreadyBooked(){
        List<LocalDate> alreadyBooked = dateRepository.findAllAvailableDates();
        return new DateBookedResponse(alreadyBooked);
    }

    public DateUnavailableAndBookedResponse getDatesAlreadyBookedAndUnavailable() {
        List<Date> dates = dateRepository.findAll();
        List<LocalDate> bookedDates = dates.stream().filter(d -> d.isAvailable()).map(d -> d.getDate()).toList();
        List<LocalDate> unavailablesDates = dates.stream().filter(d -> !d.isAvailable()).map(d -> d.getDate()).toList();
        return new DateUnavailableAndBookedResponse(
                unavailablesDates,
                bookedDates
        );
    }

    public DateUnavailableAndBookedResponse setUnavailableDates(DateSetUnavailable dateSetUnavailable) {
        LocalDate firstDay = dateSetUnavailable.firstDay();
        LocalDate lastDay = dateSetUnavailable.lastDay();

        List<LocalDate> datesBetween = firstDay
                .datesUntil(lastDay.plusDays(1L))
                .toList();

        List<LocalDate> alreadyBooked = dateRepository.findAllAvailableDates();
        List<LocalDate> alreadyUnavailable = dateRepository.findAllUnavailableDates();
        List<LocalDate> datesToPersist = datesBetween.stream()
                        .filter(date -> !alreadyBooked.contains(date)
                                && !alreadyUnavailable.contains(date))
                        .toList();
        List<LocalDate> newUnavailableDates = toLocalDate(dateRepository.saveAll(toUnavailableDates(datesToPersist)));

//        alreadyBooked.removeAll(datesBetween);
        alreadyUnavailable.addAll(newUnavailableDates);

        return new DateUnavailableAndBookedResponse(
               alreadyUnavailable,
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
