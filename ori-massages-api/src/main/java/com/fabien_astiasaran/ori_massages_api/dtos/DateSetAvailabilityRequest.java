package com.fabien_astiasaran.ori_massages_api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record DateSetAvailabilityRequest(
        @JsonProperty("from") LocalDate firstDay,
        @JsonProperty("to") LocalDate lastDay,
        @JsonProperty("availability") AvailabilityAction availability
){

}
