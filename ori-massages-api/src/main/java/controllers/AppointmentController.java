package controllers;

import dtos.CreateAppointment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import services.AppointmentService;

@RestController
@RequestMapping (name = "/appointment")
public class AppointmentController {

    private AppointmentService appointmentService;

    public AppointmentController (AppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAppointment(@RequestBody CreateAppointment createAppointment){
        appointmentService.createAppointment();
    }
}
