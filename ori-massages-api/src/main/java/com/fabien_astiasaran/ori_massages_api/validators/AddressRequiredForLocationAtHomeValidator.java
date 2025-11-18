package com.fabien_astiasaran.ori_massages_api.validators;

import com.fabien_astiasaran.ori_massages_api.dtos.AppointmentCreate;
import com.fabien_astiasaran.ori_massages_api.entities.Location;
import com.fabien_astiasaran.ori_massages_api.repositories.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;

public class AddressRequiredForLocationAtHomeValidator implements ConstraintValidator<AddressRequiredForLocationAtHome, AppointmentCreate> {

    private LocationRepository locationRepository;

    public AddressRequiredForLocationAtHomeValidator(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public boolean isValid(AppointmentCreate appointmentCreate, ConstraintValidatorContext context){
        Location location = locationRepository.findById(appointmentCreate.locationId())
                .orElseThrow(() -> new EntityNotFoundException("No Location found with "));
        if(location.isAtHome()) {
            return !isNull(appointmentCreate.address());
        }
        return true;
    }
}
