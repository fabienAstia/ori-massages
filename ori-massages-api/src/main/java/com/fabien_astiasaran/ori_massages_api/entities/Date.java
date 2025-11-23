package com.fabien_astiasaran.ori_massages_api.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "t_dates")
public class Date {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_value")
    private LocalDate date;

    @Column(name = "date_status")
    @Enumerated(EnumType.STRING)
    private DateStatus dateStatus;

    @Column(name = "comment")
    private String comment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date = (Date) o;
        return Objects.equals(this.date, date.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date);
    }

    @Override
    public String toString() {
        return "Date{" +
                "id=" + id +
                ", date=" + date +
                ", dateStatus='" + dateStatus + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public DateStatus getDateStatus() {
        return dateStatus;
    }

    public void setDateStatus(DateStatus dateStatus) {
        this.dateStatus = dateStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
