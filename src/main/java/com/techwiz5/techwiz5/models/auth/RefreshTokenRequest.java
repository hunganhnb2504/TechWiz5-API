package com.techwiz5.techwiz5.models.auth;


import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String token;
}