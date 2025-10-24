package com.fabien_astiasaran.ori_massages_api.mappers;

import com.fabien_astiasaran.ori_massages_api.dtos.LocationCreate;
import com.fabien_astiasaran.ori_massages_api.dtos.LocationResponse;
import com.fabien_astiasaran.ori_massages_api.entities.Location;

public class LocationMapper {

    public LocationResponse toResponse(Location location){
        return new LocationResponse(
                location.getId(),
                location.getName(),
                location.getAddress(),
                location.getImagePath()
        );
    }

}
