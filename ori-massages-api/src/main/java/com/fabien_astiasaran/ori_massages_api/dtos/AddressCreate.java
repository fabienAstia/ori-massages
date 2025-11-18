package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressCreate(
        @NotBlank @Size(max = 10) String streetNumber,
        @NotBlank String streetName,
        String complement,
        @NotBlank @Pattern(regexp="^[0-9]{5}$", message = "Le code postal doit contenir exactement 5 chiffres")
        String zipCode,
        @NotBlank String cityName
) {
}
