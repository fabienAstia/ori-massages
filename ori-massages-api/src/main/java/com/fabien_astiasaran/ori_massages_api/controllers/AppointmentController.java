package com.fabien_astiasaran.ori_massages_api.controllers;

import com.fabien_astiasaran.ori_massages_api.dtos.AppointmentCreate;
import com.fabien_astiasaran.ori_massages_api.entities.Appointment;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.fabien_astiasaran.ori_massages_api.services.AppointmentService;

@RestController
@RequestMapping ("/appointment")
public class AppointmentController {

    private AppointmentService appointmentService;

    public AppointmentController (AppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment createAppointment(@Valid @RequestBody AppointmentCreate appointmentCreate){
        return appointmentService.createAppointment(appointmentCreate);
    }
}
