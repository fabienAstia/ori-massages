package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.AddressCreate;
import com.fabien_astiasaran.ori_massages_api.entities.City;
import com.fabien_astiasaran.ori_massages_api.repositories.CityRepository;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City findOrCreateCity(AddressCreate addressCreate){
        return findOrCreateCity(addressCreate.zipCode(), addressCreate.city());
    }

    public City findOrCreateCity(String zipCode, String city){
        if(zipCode.isBlank() || city.isBlank()){
            throw new IllegalArgumentException(("Zip code and city name are required"));
        }
        return cityRepository.findByZipCodeAndCityName(zipCode, city)
                .orElseGet(()-> cityRepository.save(new City(zipCode, city.toUpperCase())));
    }
}
