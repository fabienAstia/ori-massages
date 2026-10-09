package com.fabien_astiasaran.ori_massages_api.entities;

public enum AppointmentStatus {
    CONFIRMED("Confirmé", 1, "#28a745"),
    COMPLETED("Terminé", 2, "#6c757d"),
    CANCELLED("Annulé", 3, "#dc3545"),
    NO_SHOW("Absent", 4, "#ffc107");

    private final String label;
    private final int order;
    private final String color;

    AppointmentStatus(String label, int order, String color) {
        this.label = label;
        this.order = order;
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public int getOrder() {
        return order;
    }

    public String getColor() {
        return color;
    }
}
