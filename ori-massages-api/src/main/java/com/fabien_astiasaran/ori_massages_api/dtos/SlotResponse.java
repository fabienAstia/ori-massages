package com.fabien_astiasaran.ori_massages_api.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public record SlotResponse(
        Long id,
//        LocalTime beginAt,
//        LocalTime endVisible,
//        LocalTime endReal,
        String beginAt,
        String endVisible,
        String endReal,
        LocalDate date,
        WorkingHoursResponse workingHours,
        PrestationResponse prestation
) {
}
