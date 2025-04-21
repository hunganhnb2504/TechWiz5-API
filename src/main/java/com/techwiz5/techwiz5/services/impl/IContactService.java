package com.techwiz5.techwiz5.services.impl;

import com.techwiz5.techwiz5.dtos.contact.ContactDTO;
import com.techwiz5.techwiz5.entities.Contact;
import com.techwiz5.techwiz5.models.contact.CreateContactRequest;
import com.techwiz5.techwiz5.repositories.ContactRepository;
import com.techwiz5.techwiz5.services.ContactService;
import com.techwiz5.techwiz5.mappers.ContactMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IContactService implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Override
    public ContactDTO saveContact(CreateContactRequest request) {
        Contact contact = Contact.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .message(request.getMessage())
                .build();
        contactRepository.save(contact);
        return contactMapper.contactDTO(contact);
    }
}
