package com.techwiz5.techwiz5.dtos.auth;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;
}
