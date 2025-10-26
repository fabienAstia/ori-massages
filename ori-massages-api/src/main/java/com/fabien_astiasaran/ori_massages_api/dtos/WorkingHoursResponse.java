package com.fabien_astiasaran.ori_massages_api.dtos;

import java.time.LocalTime;

public record WorkingHoursResponse(
        Long id,
        LocalTime startTime,
        LocalTime endTime,
        String name
) {
}
