package com.fabien_astiasaran.ori_massages_api.controllers;

import com.fabien_astiasaran.ori_massages_api.services.DateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dates")
public class DateController {
    private DateService dateService;

    public DateController(DateService dateService) {
        this.dateService = dateService;
    }

    public void postDate(){

    }
}
