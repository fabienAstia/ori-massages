package com.fabien_astiasaran.ori_massages_api.mappers;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminDurationResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.DurationResponse;
import com.fabien_astiasaran.ori_massages_api.entities.Duration;

import java.util.List;

public final class DurationMapper {

    private DurationMapper(){}

    public static DurationResponse toResponse(Duration duration){
        return new DurationResponse(
                duration.getId(),
                duration.getValue(),
                duration.getLabel(),
                duration.getBreakTime()
        );
    }

    public static List<AdminDurationResponse> toAdminResponse(List<Duration> durations){
        return durations.stream().map(DurationMapper::toAdminResponse).toList();
    }

    public static AdminDurationResponse toAdminResponse(Duration duration){
        return new AdminDurationResponse(
                duration.getId(),
                duration.getValue(),
                duration.getLabel(),
                duration.getBreakTime()
        );
    }
}
