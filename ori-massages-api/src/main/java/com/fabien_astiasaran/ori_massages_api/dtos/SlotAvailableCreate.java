package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SlotAvailableCreate(
        LocalDate date,
        @NotNull Long prestationId,
        @NotNull Long durationId
) {
}
