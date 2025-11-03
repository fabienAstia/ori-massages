package com.fabien_astiasaran.ori_massages_api.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "t_streets", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"street_name", "city_id"})
})
public class Street {

    public Street() {
        //for Hibernate
    }

    public Street(String streetName, City city) {
        this.streetName = streetName;
        this.city = city;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "street_name")
    private String streetName;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Street street = (Street) o;
        return Objects.equals(streetName, street.streetName)
                && Objects.equals(city, street.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetName, city);
    }

    @Override
    public String toString() {
        return "Street{" +
                "id=" + id +
                ", name='" + streetName + '\'' +
                ", city=" + city +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
