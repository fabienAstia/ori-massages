package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.LocationResponse;
import com.fabien_astiasaran.ori_massages_api.repositories.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    private LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<LocationResponse> getLocations(){
        List<LocationResponse> locations = new ArrayList<>();
        locationRepository.findAll().forEach(location -> {
            locations.add(
                    new LocationResponse(
                            location.getId(),
                            location.getName(),
                            location.getAddress(),
                            location.getImagePath()
                    )
            );
        });
        return locations;
    }
}
