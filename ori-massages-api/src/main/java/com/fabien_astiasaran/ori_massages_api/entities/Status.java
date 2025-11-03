package com.fabien_astiasaran.ori_massages_api.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "t_statuses")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status_label")
    private String statusLabel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return Objects.equals(statusLabel, status.statusLabel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusLabel);
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", label='" + statusLabel + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return statusLabel;
    }

    public void setLabel(String label) {
        this.statusLabel = label;
    }
}
