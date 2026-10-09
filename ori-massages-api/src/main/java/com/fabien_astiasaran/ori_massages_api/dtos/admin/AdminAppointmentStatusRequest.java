package com.fabien_astiasaran.ori_massages_api.dtos.admin;

public record AdminAppointmentStatusRequest(
    String code,
    String label,
    Integer order,
    String color
) {
}
