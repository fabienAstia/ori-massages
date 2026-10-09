package com.fabien_astiasaran.ori_massages_api.exceptions;

public class PrestationNotFoundException extends RuntimeException{

    public PrestationNotFoundException(String errorMessage){
        super(errorMessage);
    }

    public PrestationNotFoundException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
}
