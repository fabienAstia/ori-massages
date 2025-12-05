package com.fabien_astiasaran.ori_massages_api.dtos;

import com.fabien_astiasaran.ori_massages_api.entities.AppointmentStatus;

import java.time.LocalDate;

public record AppointmentResponse(
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
