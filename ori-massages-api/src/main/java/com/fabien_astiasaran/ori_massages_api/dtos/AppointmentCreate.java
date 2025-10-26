package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AppointmentCreate(
     String comment,
     @Valid SlotCreate slot,
     @Valid LocationCreate location,
     @Valid UserCreate user
) {
}
