package com.fabien_astiasaran.ori_massages_api.dtos.admin;

public record AdminWorkingHoursResponse(
        Long id,
        String startTime,
        String endTime,
        String name
) {
}
