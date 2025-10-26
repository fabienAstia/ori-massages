package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record SlotCreate (
        Long id,
        @NotNull LocalTime beginAt,
        @NotNull LocalTime endVisible,
        @NotNull LocalTime endReal,
        LocalDate date,
        @NotNull WorkingHoursResponse workingHours,
        @NotNull PrestationResponse prestation
){
}
