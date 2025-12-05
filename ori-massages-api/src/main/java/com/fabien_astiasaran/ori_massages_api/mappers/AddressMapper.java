package com.fabien_astiasaran.ori_massages_api.mappers;

import com.fabien_astiasaran.ori_massages_api.entities.Address;

import static java.util.Objects.isNull;

public final class AddressMapper {

    public AddressMapper() {
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
}
