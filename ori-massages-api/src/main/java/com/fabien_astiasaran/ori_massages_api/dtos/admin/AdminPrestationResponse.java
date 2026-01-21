package com.fabien_astiasaran.ori_massages_api.dtos.admin;

public record AdminPrestationResponse(
        Long id,
        Long typeId,
        String typeName,
        Long durationId,
        String durationLabel,
        String name,
        String description,
        Double price,
        Integer displayOrder,
        boolean active,
        String imagePath
) {
}
