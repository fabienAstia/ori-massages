package dtos;

import entities.Customer;
import entities.Location;
import entities.Service;
import entities.Status;

import java.time.LocalDateTime;

public record CreateAppointment(

     LocalDateTime beginAt,

     LocalDateTime endAt,

     String customerAddress,

     String comment,

     Customer customer,

     Location location,

     Service service,

     Status status
) {
}
