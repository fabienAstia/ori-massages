package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.constraints.NotNull;

public record ContactCreate(
        @NotNull UserCreate user,
        String message
) {
}
