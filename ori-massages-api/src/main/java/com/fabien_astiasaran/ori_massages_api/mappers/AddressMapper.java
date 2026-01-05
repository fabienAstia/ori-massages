package com.fabien_astiasaran.ori_massages_api.mappers;

import com.fabien_astiasaran.ori_massages_api.dtos.AddressCreate;
import com.fabien_astiasaran.ori_massages_api.dtos.AddressResponse;
import com.fabien_astiasaran.ori_massages_api.entities.Address;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public final class AddressMapper {

    private ObjectMapper objectMapper;

    public AddressMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public static String getFullAddress(Address address){
        return String.format("%s %s, %s %s %s",
                address.getStreetNumber(),
                address.getStreet().getStreetName(),
                address.getStreet().getCity().getZipCode(),
                address.getStreet().getCity().getCityName(),
                isNull(address.getComplement()) ? "" : ("- " + address.getComplement())
                );
    }

    public AddressCreate toAddressCreate(String addressJson) {
        try {
            return objectMapper.readValue(addressJson, AddressCreate.class);
        } catch (JsonProcessingException ex){
            throw new IllegalArgumentException("Addresse Json invalide", ex);
        }
    }

    public static AddressResponse toAddressResponse(Address address){
        return new AddressResponse(
                address.getStreetNumber(),
                address.getStreet().getStreetName(),
                address.getComplement(),
                address.getStreet().getCity().getZipCode(),
                address.getStreet().getCity().getCityName()
        );
    }
}
