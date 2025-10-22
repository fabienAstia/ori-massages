package com.fabien_astiasaran.ori_massages_api.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "t_services", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"duration_id", "type_id"})
})
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "is_active")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "duration_id")
    private Duration duration;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private ServiceType serviceType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(duration, service.duration)
                && Objects.equals(serviceType, service.serviceType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration, serviceType);
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", active=" + active +
                ", duration=" + duration +
                ", serviceType=" + serviceType +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }
}
