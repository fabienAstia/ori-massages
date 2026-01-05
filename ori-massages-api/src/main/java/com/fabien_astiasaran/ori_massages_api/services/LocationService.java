package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.AddressCreate;
import com.fabien_astiasaran.ori_massages_api.dtos.LocationCreate;
import com.fabien_astiasaran.ori_massages_api.dtos.LocationResponse;
import com.fabien_astiasaran.ori_massages_api.entities.Address;
import com.fabien_astiasaran.ori_massages_api.entities.Location;
import com.fabien_astiasaran.ori_massages_api.mappers.AddressMapper;
import com.fabien_astiasaran.ori_massages_api.repositories.AddressRepository;
import com.fabien_astiasaran.ori_massages_api.repositories.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static com.fabien_astiasaran.ori_massages_api.mappers.LocationMapper.toResponse;

@Service
public class LocationService {

    @Value("${uploads.storage.path}/locations")
    private String upLoadsLoc;

    private final LocationRepository locationRepository;
    private final AddressService addressService;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public LocationService(LocationRepository locationRepository, AddressService addressService, AddressRepository addressRepository, AddressMapper addressMapper) {
        this.locationRepository = locationRepository;
        this.addressService = addressService;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
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
        String imageId = buildImageId(image);
        storeImage(image, imageId);

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

        if(!image.isEmpty()){
            String imageId;
            try{
                imageId = buildImageId(image);
                storeImage(image, imageId);
            }catch(Exception ex){
                throw new RuntimeException(ex);
            }
            deletePreviousImage(location);
            location.setImagePath(imageId);
        }
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

    private String buildImageId(MultipartFile image) {
        UUID uuid = UUID.randomUUID();
        String name = image.getOriginalFilename();
        int index = name.lastIndexOf('.');
        String ext = name.substring(index, name.length());
        return uuid + ext;
    }

    private void storeImage(MultipartFile image, String imageId){
        try{
            String dest = String.format("%s/%s", upLoadsLoc, imageId);
            File file = new File(dest);
            image.transferTo(file);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    private boolean deletePreviousImage(Location location){
        String oldImagePath = String.format("%s/%s", upLoadsLoc, location.getImagePath());
        File fileToDelete = new File(oldImagePath);
        return fileToDelete.delete();
    }

    public void deleteLocation(Long id){
        locationRepository.deleteById(id);
    }

}
