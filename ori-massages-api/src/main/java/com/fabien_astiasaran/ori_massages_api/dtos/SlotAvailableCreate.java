package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.Valid;

import java.time.LocalDate;

public record SlotAvailableCreate(
        LocalDate date,
        @Valid PrestationCreate prestation
) {
}
