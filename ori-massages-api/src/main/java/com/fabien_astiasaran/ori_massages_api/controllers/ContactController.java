package com.fabien_astiasaran.ori_massages_api.controllers;

import com.fabien_astiasaran.ori_massages_api.dtos.UserCreate;
import com.fabien_astiasaran.ori_massages_api.services.ContactService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
public class ContactController {

    private ContactService contactService;

    public ContactController(ContactService contactService){
        this.contactService=contactService;
    }

    @PostMapping
    public void createContact(@Valid @RequestBody UserCreate customer){
        System.out.println("create contact= " + customer.toString());
        contactService.createContact(customer);
    }
}
