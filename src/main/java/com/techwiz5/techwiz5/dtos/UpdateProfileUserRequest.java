package com.techwiz5.techwiz5.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UpdateProfileUserRequest {
    private String preferredCurrency;
//    private List<> travelPreferences;
    private String profilePictureUrl;
}