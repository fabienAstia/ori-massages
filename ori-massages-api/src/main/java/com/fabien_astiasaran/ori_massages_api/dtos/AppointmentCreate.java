package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record AppointmentCreate(

     @Valid
     ServiceCreate service,

     LocalDate date,

     String hours,

     @NotBlank
     String location,

     @Valid
     UserCreate customer,

     String customerAddress
) {
}
