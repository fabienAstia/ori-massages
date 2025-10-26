package com.fabien_astiasaran.ori_massages_api.mappers;

import com.fabien_astiasaran.ori_massages_api.dtos.PrestationResponse;
import com.fabien_astiasaran.ori_massages_api.entities.Prestation;

public class PrestationMapper {
    public static PrestationResponse toResponse(Prestation prestation) {
        return new PrestationResponse(
                prestation.getId(),
                prestation.getName(),
                prestation.getDescription(),
                prestation.getPrice(),
                prestation.isActive(),
                prestation.getImagePath(),
                DurationMapper.toResponse(prestation.getDuration())
        );
    }
}
