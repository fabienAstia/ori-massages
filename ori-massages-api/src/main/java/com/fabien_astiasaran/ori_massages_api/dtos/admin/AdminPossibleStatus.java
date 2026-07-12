package com.fabien_astiasaran.ori_massages_api.dtos.admin;

import com.fabien_astiasaran.ori_massages_api.entities.AppointmentStatus;

public record AdminPossibleActions(
        AppointmentStatus nextStatus,
        String label
) {
}
