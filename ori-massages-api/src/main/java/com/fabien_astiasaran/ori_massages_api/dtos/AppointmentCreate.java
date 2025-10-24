package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.Valid;

import java.time.LocalDate;

public record AppointmentCreate(

     @Valid PrestationCreate prestation,

     LocalDate date,

     String hours,

     @Valid LocationCreate location,

     @Valid UserCreate user,

     String customerAddress
) {
}
