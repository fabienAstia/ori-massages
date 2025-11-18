package com.fabien_astiasaran.ori_massages_api.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Constraint(validatedBy = AddressRequiredForLocationAtHomeValidator.class)
public @interface AddressRequiredForLocationAtHome {
    String message() default "Address is required when selected Location is At Home";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
