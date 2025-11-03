package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.LocationResponse;
import com.fabien_astiasaran.ori_massages_api.entities.Address;
import com.fabien_astiasaran.ori_massages_api.entities.Location;
import com.fabien_astiasaran.ori_massages_api.mappers.LocationMapper;
import com.fabien_astiasaran.ori_massages_api.repositories.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.fabien_astiasaran.ori_massages_api.mappers.LocationMapper.toResponse;

@Service
public class LocationService {

    private LocationRepository locationRepository;
    private AddressService addressService;
    private static final String TREATMENT_ROOM = "Espace soin";

    public LocationService(LocationRepository locationRepository, AddressService addressService) {
        this.locationRepository = locationRepository;
        this.addressService = addressService;
    }

    public List<LocationResponse> getLocations(){
        var locations = locationRepository.findAll();
        return locations.stream().map(location -> {
            if(location.getName().equalsIgnoreCase(TREATMENT_ROOM)){
                Address address = addressService.findByLocation(location);
                return toResponse(location, address);
            } else {
                return toResponse(location);
            }
        }).collect(Collectors.toList());
    }
}
