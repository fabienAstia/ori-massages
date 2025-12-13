package com.fabien_astiasaran.ori_massages_api.dtos.admin;

import com.fabien_astiasaran.ori_massages_api.entities.AppointmentStatus;

import java.time.LocalDate;

public record AdminAppointmentResponse(
        Long id,
        String userFullName,
        String prestationName,
        String beginAt,
        String endReal,
        LocalDate dateMeeting,
        LocalDate dateCreation,
        boolean atHome,
        String locationName,
        String address,
        AppointmentStatus status
) {
}
