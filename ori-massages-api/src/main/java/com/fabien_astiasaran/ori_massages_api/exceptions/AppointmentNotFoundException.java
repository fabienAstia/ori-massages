package com.fabien_astiasaran.ori_massages_api.exceptions;

public class AppointmentNotFoundException extends RuntimeException{

    public AppointmentNotFoundException(String errorMessage){
        super(errorMessage);
    }

    public AppointmentNotFoundException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
}
