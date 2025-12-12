package com.fabien_astiasaran.ori_massages_api.dtos.admin;

public record AdminDurationResponse(
        Long id,
        Integer value,
        String label,
        Integer breakTime
) {
}
