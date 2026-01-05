package com.fabien_astiasaran.ori_massages_api.controllers;

import com.fabien_astiasaran.ori_massages_api.dtos.LocationCreate;
import com.fabien_astiasaran.ori_massages_api.dtos.LocationResponse;
import com.fabien_astiasaran.ori_massages_api.services.LocationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public List<LocationResponse> getLocations(){
        return locationService.getLocations();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocationResponse createLocation(@Valid @ModelAttribute LocationCreate locationCreate){
        return locationService.createLocation(locationCreate);
    }

    @PostMapping("/{id}")
    public LocationResponse editLocation(@PathVariable Long id, @Valid @ModelAttribute LocationCreate locationCreate){
        return locationService.editLocation(id, locationCreate);
    }

    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable Long id){
        locationService.deleteLocation(id);
    }
}
