package com.fabien_astiasaran.ori_massages_api.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "t_durations")
public class Duration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "duration_value")
    private int value;

    @Column(name = "label")
    private String label;

    @Column(name = "break_time")
    private int breakTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Duration duration = (Duration) o;
        return value == duration.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    @Override
    public String toString() {
        return "Duration{" +
                "durationId=" + id +
                ", value=" + value +
                ", label='" + label + '\'' +
                ", break_time='" + breakTime + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(int breakTime) {
        this.breakTime = breakTime;
    }
}
