package com.fabien_astiasaran.ori_massages_api.mappers;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminTreatmentTypeResponse;
import com.fabien_astiasaran.ori_massages_api.entities.TreatmentType;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class TreatmentTypeMapper {

    private TreatmentTypeMapper() {
    }

    public static Set<AdminTreatmentTypeResponse> toResponse(List<TreatmentType> types){
        return types.stream().map(TreatmentTypeMapper::toResponse).collect(Collectors.toSet());
    }

    public static AdminTreatmentTypeResponse toResponse(TreatmentType type){
        return new AdminTreatmentTypeResponse(
                type.getId(),
                type.getName(),
                type.getDescription()
        );
    }
}
