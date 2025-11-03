package com.fabien_astiasaran.ori_massages_api.mappers;

import com.fabien_astiasaran.ori_massages_api.entities.Date;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DateMapper {

    public static List<LocalDate> toLocalDate(List<Date> dates){
        return dates.stream()
                .map(Date::getDate)
                .collect(Collectors.toUnmodifiableList());
    }
}
