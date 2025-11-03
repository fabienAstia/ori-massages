package com.fabien_astiasaran.ori_massages_api.mappers;

import com.fabien_astiasaran.ori_massages_api.dtos.LocationResponse;
import com.fabien_astiasaran.ori_massages_api.entities.Address;
import com.fabien_astiasaran.ori_massages_api.entities.Location;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isBlank;

public final class LocationMapper {

    private LocationMapper() {
    }

    public static List<LocationResponse> toResponse(List<Location> locations){
        return locations.stream()
                .map(location -> toResponse(location))
                .collect(Collectors.toList());
    }

    public static LocationResponse toResponse(Location location){
        return new LocationResponse(
                location.getId(),
                location.getName(),
                location.getImagePath(),
                null
                );
    }

    public static LocationResponse toResponse(Location location, Address address){
        return new LocationResponse(
                location.getId(),
                location.getName(),
                location.getImagePath(),
                formatAddress(address)
        );
    }

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
