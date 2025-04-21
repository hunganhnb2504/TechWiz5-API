package com.techwiz5.techwiz5.models.auth;
import lombok.Data;

@Data
public class SignUpRequest {
    private String fullname;
    private String email;
    private String password;
}
