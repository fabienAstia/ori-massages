package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.AddressCreate;
import com.fabien_astiasaran.ori_massages_api.dtos.LocationCreate;
import com.fabien_astiasaran.ori_massages_api.dtos.LocationResponse;
import com.fabien_astiasaran.ori_massages_api.entities.Address;
import com.fabien_astiasaran.ori_massages_api.entities.Location;
import com.fabien_astiasaran.ori_massages_api.mappers.AddressMapper;
import com.fabien_astiasaran.ori_massages_api.repositories.AddressRepository;
import com.fabien_astiasaran.ori_massages_api.repositories.LocationRepository;
import com.fabien_astiasaran.ori_massages_api.utils.ImageCategory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.fabien_astiasaran.ori_massages_api.mappers.LocationMapper.toResponse;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final AddressService addressService;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final ImageService imageService;

    public LocationService(LocationRepository locationRepository, AddressService addressService, AddressRepository addressRepository, AddressMapper addressMapper, ImageService imageService) {
        this.locationRepository = locationRepository;
        this.addressService = addressService;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.imageService = imageService;
    }

    public List<LocationResponse> getLocations(){
        var locations = locationRepository.findAll();
        return locations.stream().map(location -> {
            if(!location.isAtHome()){
                Address address = addressService.findByLocation(location);
                return toResponse(location, address);
            } else {
                return toResponse(location);
            }
        }).toList();
    }

    @Transactional
    public LocationResponse createLocation(LocationCreate locationCreate){
        MultipartFile image = locationCreate.getImage();
        String imageId = imageService.buildImageId(image);
        imageService.storeImage(image, imageId, ImageCategory.LOCATION);

        Location newLocation = new Location();
        newLocation.setName(locationCreate.getName());
        newLocation.setAtHome(false);
        newLocation.setImagePath(imageId);
        locationRepository.save(newLocation);

        AddressCreate addressCreate = addressMapper.toAddressCreate(locationCreate.getAddress());
        Address locationAddress = addressService.findOrCreateAddress(addressCreate);
        locationAddress.setLocation(newLocation);
        addressRepository.save(locationAddress);

        return toResponse(newLocation, locationAddress);
    }

    @Transactional
    public LocationResponse editLocation(Long id, LocationCreate locationCreate){
        Location location = locationRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("There is no Location found with this ID"));
        location.setName(locationCreate.getName());
        MultipartFile image = locationCreate.getImage();

        handleImage(image, location);
        locationRepository.save(location);

        if(!locationCreate.isAtHome()){
            AddressCreate addressCreate = addressMapper.toAddressCreate(locationCreate.getAddress());
            Address locationAddress = addressService.findOrCreateAddress(addressCreate);
            locationAddress.setLocation(location);
            addressRepository.save(locationAddress);
            return toResponse(location, locationAddress);
        }
        return toResponse(location);
    }

    private void handleImage(MultipartFile image, Location location) {
        if(!image.isEmpty()){
            String imageId = imageService.buildImageId(image);
            imageService.storeImage(image, imageId, ImageCategory.LOCATION);
            imageService.deletePreviousImage(location.getImagePath(), ImageCategory.LOCATION);
            location.setImagePath(imageId);
        }
    }

    public void deleteLocation(Long id){
        locationRepository.deleteById(id);
    }

}
