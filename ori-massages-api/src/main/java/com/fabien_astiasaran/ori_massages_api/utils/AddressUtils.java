package com.fabien_astiasaran.ori_massages_api.utils;

import com.fabien_astiasaran.ori_massages_api.entities.Address;

import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isBlank;

public final class AddressUtils {

    private AddressUtils() {}

    public static String formatAddress(Address address){
        return String.format("%s %s %s %s %s",
                address.getStreetNumber(),
                address.getStreet().getStreetName(),
                address.getStreet().getCity().getZipCode(),
                address.getStreet().getCity().getCityName(),
                getComplement(address));
    }

    private static String getComplement(Address address) {
        if(isNull(address.getComplement()) || isBlank(address.getComplement())){
            return "";
        }
        return address.getComplement();
    }
}
