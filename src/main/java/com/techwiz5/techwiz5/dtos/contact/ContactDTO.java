package com.techwiz5.techwiz5.dtos.contact;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String message;
}
