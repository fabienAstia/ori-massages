package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.constraints.Max;

public record MessageCreate(
        @Max(300)String content
) {
}
