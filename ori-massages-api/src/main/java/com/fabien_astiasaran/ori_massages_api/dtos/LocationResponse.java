package com.fabien_astiasaran.ori_massages_api.dtos;

public record LocationResponse(
        Long id,
        String name,
        String imagePath,
        boolean atHome,
        String address
) {
}
