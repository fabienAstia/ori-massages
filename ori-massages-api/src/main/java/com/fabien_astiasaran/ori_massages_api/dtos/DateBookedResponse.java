package com.fabien_astiasaran.ori_massages_api.dtos;

import java.time.LocalDate;
import java.util.List;

public record DateBookedResponse(
        List<LocalDate> bookedDays

) {
}
