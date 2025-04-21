package com.techwiz5.techwiz5.mappers;

import com.techwiz5.techwiz5.dtos.contact.ContactDTO;
import com.techwiz5.techwiz5.entities.Contact;
import com.techwiz5.techwiz5.exceptions.AppException;
import com.techwiz5.techwiz5.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {
    public ContactDTO contactDTO (Contact model) {
        if (model == null) throw new AppException(ErrorCode.NOTFOUND);
        ContactDTO contactDTO = ContactDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .phone(model.getPhone())
                .email(model.getEmail())
                .message(model.getMessage())
                .build();
        return contactDTO;
    }
}
