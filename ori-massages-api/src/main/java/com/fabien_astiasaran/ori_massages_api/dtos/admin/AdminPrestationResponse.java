package com.fabien_astiasaran.ori_massages_api.dtos.admin;

import com.fabien_astiasaran.ori_massages_api.dtos.DurationResponse;

public record AdminPrestationResponse(
        Long id,
        AdminTreatmentTypeResponse type,
        DurationResponse duration,
        String name,
        String description,
        Double price,
        boolean active,
        String imagePath
) {
}
