package com.fabien_astiasaran.ori_massages_api.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "t_addresses", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"street_number", "complement", "street_id"})
})
public class Address {

    public Address() {
        //for hibernate
    }

    public Address(String streetNumber, String complement, Street street, User user, Location location) {
        this.streetNumber = streetNumber;
        this.complement = complement;
        this.street = street;
        this.user = user;
        this.location = location;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "complement")
    private String complement;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "street_id")
    private Street street;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(streetNumber, address.streetNumber) && Objects.equals(complement, address.complement) && Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetNumber, complement, street);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", streetNumber='" + streetNumber + '\'' +
                ", complement='" + complement + '\'' +
                ", user=" + user +
                ", location=" + location +
                ", street=" + street +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }
}
