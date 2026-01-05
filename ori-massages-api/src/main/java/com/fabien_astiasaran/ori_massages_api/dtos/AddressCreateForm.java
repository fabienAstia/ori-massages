package com.fabien_astiasaran.ori_massages_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AddressCreateForm {

    @NotBlank
    @Size(max = 10)
    private String streetNumber;

    @NotBlank
    private String streetName;

    private String complement;

    @NotBlank @Pattern(regexp="^[0-9]{5}$", message = "Le code postal doit contenir exactement 5 chiffres")
    private String zipCode;

    @NotBlank
    private String cityName;

    public AddressCreateForm() {
    }

    public @NotBlank @Size(max = 10) String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(@NotBlank @Size(max = 10) String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public @NotBlank String getStreetName() {
        return streetName;
    }

    public void setStreetName(@NotBlank String streetName) {
        this.streetName = streetName;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public @NotBlank @Pattern(regexp = "^[0-9]{5}$", message = "Le code postal doit contenir exactement 5 chiffres") String getZipCode() {
        return zipCode;
    }

    public void setZipCode(@NotBlank @Pattern(regexp = "^[0-9]{5}$", message = "Le code postal doit contenir exactement 5 chiffres") String zipCode) {
        this.zipCode = zipCode;
    }

    public @NotBlank String getCityName() {
        return cityName;
    }

    public void setCityName(@NotBlank String cityName) {
        this.cityName = cityName;
    }
}
