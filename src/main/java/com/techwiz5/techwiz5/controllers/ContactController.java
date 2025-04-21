package com.techwiz5.techwiz5.controllers;

import com.techwiz5.techwiz5.dtos.ResponseObject;
import com.techwiz5.techwiz5.dtos.contact.ContactDTO;
import com.techwiz5.techwiz5.models.contact.CreateContactRequest;
import com.techwiz5.techwiz5.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/any/contact")
    public ResponseEntity<ResponseObject> createContact(@RequestBody CreateContactRequest request) {
        ContactDTO contactDTO = contactService.saveContact(request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Contact message sent successfully", contactDTO)
        );
    }
}
