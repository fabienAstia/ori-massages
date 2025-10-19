package entities;

import jakarta.persistence.*;

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
