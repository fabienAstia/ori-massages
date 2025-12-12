package com.fabien_astiasaran.ori_massages_api.dtos.admin;

import com.fabien_astiasaran.ori_massages_api.entities.AppointmentStatus;

import java.time.LocalDate;

public record AdminAppointmentResponse(
        Long id,
        String userFullName,
        String prestationName,
        LocalDate date,
        String beginAt,
        String endReal,
        boolean isAtHome,
        String locationName,
        String address,
        AppointmentStatus status
) {
}
