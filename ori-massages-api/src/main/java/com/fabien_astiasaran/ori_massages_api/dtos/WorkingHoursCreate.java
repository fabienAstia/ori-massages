package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.constraints.NotBlank;

public record WorkingHoursCreate(
        @NotBlank String startTime,
        @NotBlank String endTime,
        String name
) {
}
