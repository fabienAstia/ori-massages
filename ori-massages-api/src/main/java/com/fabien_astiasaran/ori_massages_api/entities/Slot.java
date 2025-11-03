package com.fabien_astiasaran.ori_massages_api.entities;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "t_slots", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"begin_at", "date_id"})
})
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "begin_at")
    private LocalTime beginAt;

    @Column(name = "end_at")
    private LocalTime endAt;

    @ManyToOne
    @JoinColumn(name = "date_id")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "working_hours_id")
    private WorkingHours workingHours;

    @ManyToOne
    @JoinColumn(name = "prestation_id")
    private Prestation prestation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return Objects.equals(beginAt, slot.beginAt)
                && Objects.equals(date, slot.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beginAt, date);
    }

    @Override
    public String toString() {
        return "Slot{" +
                "id=" + id +
                ", beginAt=" + beginAt +
                ", endAt=" + endAt +
                ", date=" + date +
                ", workingHours=" + workingHours +
                ", prestation=" + prestation +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(LocalTime beginAt) {
        this.beginAt = beginAt;
    }

    public LocalTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalTime endAt) {
        this.endAt = endAt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public WorkingHours getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(WorkingHours workingHours) {
        this.workingHours = workingHours;
    }

    public Prestation getPrestation() {
        return prestation;
    }

    public void setPrestation(Prestation prestation) {
        this.prestation = prestation;
    }
}
