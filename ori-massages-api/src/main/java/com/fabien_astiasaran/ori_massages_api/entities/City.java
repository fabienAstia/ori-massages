package com.fabien_astiasaran.ori_massages_api.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "t_cities", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"zip_code", "city_name"})
})
public class City {

    public City() {
        // for Hibernate
    }

    public City(String zipCode, String cityName) {
        this.zipCode = zipCode;
        this.cityName = cityName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "city_name")
    private String cityName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(zipCode, city.zipCode) && Objects.equals(cityName, city.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zipCode, cityName);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", zipCode='" + zipCode + '\'' +
                ", name='" + cityName + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
