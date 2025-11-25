package com.fabien_astiasaran.ori_massages_api.exceptions;

import java.time.LocalDate;

public class DateClosedException extends RuntimeException{

    public DateClosedException(LocalDate date){
        super("The date " + date + " is closed and cannot generate slots");
    }
}
