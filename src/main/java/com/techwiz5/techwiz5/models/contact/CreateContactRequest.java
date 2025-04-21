package com.techwiz5.techwiz5.models.contact;

import lombok.Data;

@Data
public class CreateContactRequest {
    private String name;
    private String email;
    private String phone;
    private String message;
}
