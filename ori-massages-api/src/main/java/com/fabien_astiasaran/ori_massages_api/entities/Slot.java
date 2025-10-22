package com.fabien_astiasaran.ori_massages_api.entities;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "t_slots")
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "begin_at")
    private LocalTime beginHour;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "date_id")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "working_hours_id")
    private WorkingHours workingHours;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return Objects.equals(beginHour, slot.beginHour)
                && Objects.equals(date, slot.date)
                && Objects.equals(workingHours, slot.workingHours)
                && Objects.equals(service, slot.service);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beginHour, date, workingHours, service);
    }

    @Override
    public String toString() {
        return "Slot{" +
                "id=" + id +
                ", beginHour=" + beginHour +
                ", status='" + status + '\'' +
                ", date=" + date +
                ", workingHours=" + workingHours +
                ", service=" + service +
                '}';
    }

    public Long getId() {
        return id;
    }

    public LocalTime getBeginHour() {
        return beginHour;
    }

    public void setBeginHour(LocalTime beginHour) {
        this.beginHour = beginHour;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
