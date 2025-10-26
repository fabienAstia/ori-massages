package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.AppointmentCreate;
import com.fabien_astiasaran.ori_massages_api.dtos.SlotAvailableCreate;
import com.fabien_astiasaran.ori_massages_api.dtos.SlotCreate;
import com.fabien_astiasaran.ori_massages_api.entities.Date;
import com.fabien_astiasaran.ori_massages_api.repositories.DateRepository;
import org.springframework.stereotype.Service;

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

    public Date findOrCreateDate(SlotAvailableCreate slotAvailable) {
        Date date = dateRepository.findByDate(slotAvailable.date())
                .orElseGet(() -> {
                    Date newDate = new Date();
                    newDate.setDate(slotAvailable.date());
                    newDate.setAvailable(true);
                    return dateRepository.save(newDate);
                });
        return date;
    }

}
