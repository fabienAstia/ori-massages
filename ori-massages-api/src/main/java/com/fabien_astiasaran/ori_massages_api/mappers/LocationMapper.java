package com.fabien_astiasaran.ori_massages_api.mappers;

import com.fabien_astiasaran.ori_massages_api.dtos.LocationResponse;
import com.fabien_astiasaran.ori_massages_api.entities.Address;
import com.fabien_astiasaran.ori_massages_api.entities.Location;

import java.util.List;

import static com.fabien_astiasaran.ori_massages_api.utils.AddressUtils.formatAddress;

public final class LocationMapper {

    private LocationMapper() {
    }

    public static List<LocationResponse> toResponse(List<Location> locations){
        return locations.stream()
                .map(location -> toResponse(location))
                .toList();
    }

    public static LocationResponse toResponse(Location location){
        return new LocationResponse(
                location.getId(),
                location.getName(),
                location.getImagePath(),
                location.isAtHome(),
                null
                );
    }

    public static LocationResponse toResponse(Location location, Address address){
        return new LocationResponse(
                location.getId(),
                location.getName(),
                location.getImagePath(),
                location.isAtHome(),
                formatAddress(address)
        );
    }

}
