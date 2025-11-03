package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.AddressCreate;
import com.fabien_astiasaran.ori_massages_api.entities.City;
import com.fabien_astiasaran.ori_massages_api.entities.Street;
import com.fabien_astiasaran.ori_massages_api.repositories.StreetRepository;
import org.springframework.stereotype.Service;

@Service
public class StreetService {
    private StreetRepository streetRepository;
    private CityService cityService;

    public StreetService(StreetRepository streetRepository, CityService cityService) {
        this.streetRepository = streetRepository;
        this.cityService = cityService;
    }

    public Street findOrCreateStreet(AddressCreate addressCreate){
        City city = cityService.findOrCreateCity(addressCreate);
        return streetRepository.findByCityAndStreetName(city, addressCreate.streetName())
                .orElseGet(() -> streetRepository.save(
                        new Street(addressCreate.streetName(), city))
                );
    }
}
