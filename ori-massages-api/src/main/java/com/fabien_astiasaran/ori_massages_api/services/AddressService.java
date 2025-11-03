package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.AddressCreate;
import com.fabien_astiasaran.ori_massages_api.entities.Address;
import com.fabien_astiasaran.ori_massages_api.entities.Location;
import com.fabien_astiasaran.ori_massages_api.repositories.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private AddressRepository addressRepository;
    private StreetService streetService;

    public AddressService(AddressRepository addressRepository, StreetService streetService) {
        this.addressRepository = addressRepository;
        this.streetService = streetService;
    }

    public Address findOrCreateAddress(AddressCreate addressCreate){
        var street = streetService.findOrCreateStreet(addressCreate);
        return addressRepository.findByStreetNumberAndComplementAndStreet(
                addressCreate.streetNumber(),
                addressCreate.complement(),
                street
        ).orElseGet(() -> addressRepository.save(new Address(
                addressCreate.streetNumber(),
                addressCreate.complement(),
                street,
                null,
                null
        )));
    }

    public Address findByLocation(Location location){
        return addressRepository.findByLocation(location);
    }
}
