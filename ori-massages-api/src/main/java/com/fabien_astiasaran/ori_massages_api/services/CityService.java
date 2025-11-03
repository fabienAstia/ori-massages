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
        return findOrCreateCity(addressCreate.zipCode(), addressCreate.cityName());
    }

    public City findOrCreateCity(String zipCode, String cityName){
        if(zipCode.isBlank() || cityName.isBlank()){
            throw new IllegalArgumentException(("Zip code and city name are required"));
        }
        return cityRepository.findByZipCodeAndCityName(zipCode, cityName)
                .orElseGet(()-> cityRepository.save(new City(zipCode, cityName)));
    }
}
