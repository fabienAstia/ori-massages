package com.fabien_astiasaran.ori_massages_api.controllers;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminAppointmentStatusRequest;
import com.fabien_astiasaran.ori_massages_api.services.AppointmentStatusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appointment-statuses")
public class AppointmentStatusController {

    private AppointmentStatusService appointmentStatusService;

    public AppointmentStatusController(AppointmentStatusService appointmentStatusService) {
        this.appointmentStatusService = appointmentStatusService;
    }

    @GetMapping
    public List<AdminAppointmentStatusRequest> getAppointmentStatuses(){
        return appointmentStatusService.getAppointmentStatuses();
    }
}
