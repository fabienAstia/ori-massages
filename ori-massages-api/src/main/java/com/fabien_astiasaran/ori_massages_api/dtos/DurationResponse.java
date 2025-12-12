package com.fabien_astiasaran.ori_massages_api.dtos;

public record DurationResponse(
        Long id,
        Integer value,
        String label,
        Integer breakTime
) {
}
