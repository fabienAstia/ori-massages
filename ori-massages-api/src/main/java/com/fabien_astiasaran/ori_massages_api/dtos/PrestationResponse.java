package com.fabien_astiasaran.ori_massages_api.dtos;

public record PrestationResponse(
        Long id,
        String typeName,
        Long durationId,
        String durationLabel,
        String name,
        String description,
        Double price,
        boolean active,
        String imagePath
) {
}
