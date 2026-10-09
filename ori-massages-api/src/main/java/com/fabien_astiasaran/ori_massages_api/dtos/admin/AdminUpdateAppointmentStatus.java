package com.fabien_astiasaran.ori_massages_api.dtos.admin;

import com.fabien_astiasaran.ori_massages_api.entities.AppointmentStatus;
import jakarta.validation.constraints.NotNull;

public record AdminUpdateAppointmentStatus(
    @NotNull AppointmentStatus targetStatus
) {
}
