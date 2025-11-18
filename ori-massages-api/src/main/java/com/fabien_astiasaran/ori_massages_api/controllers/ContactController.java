package com.fabien_astiasaran.ori_massages_api.controllers;

import com.fabien_astiasaran.ori_massages_api.dtos.Contact;
import com.fabien_astiasaran.ori_massages_api.dtos.ContactCreate;
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
    public Contact createContact(@Valid @RequestBody ContactCreate contact){
        return contactService.createContact(contact);
    }
}
