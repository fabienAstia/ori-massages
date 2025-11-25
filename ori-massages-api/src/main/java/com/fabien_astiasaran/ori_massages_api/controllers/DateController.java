package com.fabien_astiasaran.ori_massages_api.controllers;

import com.fabien_astiasaran.ori_massages_api.dtos.DateSetResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.AllDateResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.DateSetAvailabilityRequest;
import com.fabien_astiasaran.ori_massages_api.services.DateService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/dates")
public class DateController {
    private DateService dateService;

    public DateController(DateService dateService) {
        this.dateService = dateService;
    }

    @GetMapping("/booked")
    public DateSetResponse getBooked(){
        return dateService.getLockedDates();
    }

    @GetMapping("/closed")
    public DateSetResponse getUnavailable(){
        return dateService.getClosedDates();
    }

    @GetMapping
    public AllDateResponse getAllDates(){
        return dateService.getAllDates();
    }

    @PostMapping("/availability")
    public AllDateResponse setAvailability(@Valid @RequestBody DateSetAvailabilityRequest date){
        return dateService.updateAvailability(date);
    }
}
