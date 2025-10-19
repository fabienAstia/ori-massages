package entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "t_durations")
public class Duration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long durationId;

    @Column(name = "duration_value")
    private int value;

    @Column(name = "duration_label")
    private String label;

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
                "durationId=" + durationId +
                ", value=" + value +
                ", label='" + label + '\'' +
                '}';
    }

    public Long getDurationId() {
        return durationId;
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
}
