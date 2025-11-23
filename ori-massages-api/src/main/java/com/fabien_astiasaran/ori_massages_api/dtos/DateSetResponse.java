package com.fabien_astiasaran.ori_massages_api.dtos;

import java.time.LocalDate;
import java.util.Set;

public record DateSetResponse(
        Set<LocalDate> dates

) {
}
