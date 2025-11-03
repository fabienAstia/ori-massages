package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressCreate(
        @NotBlank String cityName,
        @NotBlank String zipCode,
        @NotBlank String streetName,
        @NotBlank String streetNumber,
        @NotBlank String complement,
        @NotNull Long locationId
) {
}
