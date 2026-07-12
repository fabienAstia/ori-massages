package com.fabien_astiasaran.ori_massages_api.dtos.admin;

public record AppointmentStatusRequest(
    String code,
    String label,
    Integer order,
    String color
) {
}
