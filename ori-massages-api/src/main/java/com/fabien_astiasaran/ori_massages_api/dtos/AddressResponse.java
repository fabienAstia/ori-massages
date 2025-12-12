package com.fabien_astiasaran.ori_massages_api.dtos;

public record AddressResponse (
        String streetNumber,
        String streetName,
        String zipCode,
        String city,
        String complement
){
}
