package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreate(

        @Size(min = 10, max = 14) String phoneNumber,
        @Email(message = "Please provide a valid email address.")
        @NotBlank @Size(max = 150) String email,
        @Nullable String fullname
        ) {
}
