package com.techwiz5.techwiz5.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String fullName;
    private String email;
    private String preferredCurrency;
    private List<String> travelPreferences;
    private String profilePictureUrl;
}
