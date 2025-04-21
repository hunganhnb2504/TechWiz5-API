package com.techwiz5.techwiz5.services;

import com.techwiz5.techwiz5.dtos.contact.ContactDTO;
import com.techwiz5.techwiz5.models.contact.CreateContactRequest;

public interface ContactService {
    ContactDTO saveContact(CreateContactRequest request);
}
