package com.fabien_astiasaran.ori_massages_api.mappers;

import com.fabien_astiasaran.ori_massages_api.dtos.DurationResponse;
import com.fabien_astiasaran.ori_massages_api.entities.Duration;

public class DurationMapper {

    public static DurationResponse toResponse(Duration duration){
        return new DurationResponse(
                duration.getId(),
                duration.getValue(),
                duration.getLabel(),
                duration.getBreakTime()
        );
    }
}
