package com.techwiz5.techwiz5.models.auth;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfile {
    private String preferredCurrency;
    private List<String> travelPreferences;
    private MultipartFile profilePictureUrl;
}
