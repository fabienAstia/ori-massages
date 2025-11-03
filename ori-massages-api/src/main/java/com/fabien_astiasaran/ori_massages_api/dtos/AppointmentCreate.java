package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalDateTime;


public record AppointmentCreate(
     @Valid SlotCreate slot,
     @Valid UserCreate user,
     @Valid AddressCreate address,
     String message
) {
}
