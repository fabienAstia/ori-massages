package com.fabien_astiasaran.ori_massages_api.dtos.admin;

public record AdminPrestationResponse(
        Long id,
        String typeName,
        String durationLabel,
        String name,
        String description,
        Double price,
        boolean active,
        String imagePath
) {
}
