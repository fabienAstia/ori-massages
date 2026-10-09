package com.fabien_astiasaran.ori_massages_api.dtos.admin;

import com.fabien_astiasaran.ori_massages_api.entities.AppointmentStatus;

public record AdminPossibleStatus(
        AppointmentStatus possibleStatus,
        String label,
        String color
) {
}
