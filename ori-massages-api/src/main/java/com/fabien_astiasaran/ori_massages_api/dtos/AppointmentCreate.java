package com.fabien_astiasaran.ori_massages_api.dtos;

import com.fabien_astiasaran.ori_massages_api.validators.AddressRequiredForLocationAtHome;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@AddressRequiredForLocationAtHome
public record AppointmentCreate(
     @Valid SlotCreate slot,
     @Valid UserCreate user,
     @Valid AddressCreate address,
     @NotNull Long locationId,
     String message
) {
}
