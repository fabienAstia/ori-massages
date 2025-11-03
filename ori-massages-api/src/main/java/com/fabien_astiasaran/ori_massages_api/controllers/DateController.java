package com.fabien_astiasaran.ori_massages_api.controllers;

import com.fabien_astiasaran.ori_massages_api.dtos.DateBookedResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.DateUnavailableAndBookedResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.DateSetUnavailable;
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
    public DateBookedResponse getDatesAlreadyBooked(){
        return dateService.getDatesAlreadyBooked();
    }

    @PostMapping("/unavailable")
    public DateUnavailableAndBookedResponse setUnavailable(@Valid @RequestBody DateSetUnavailable date){
        return dateService.setUnavailableDates(date);
    }
}
