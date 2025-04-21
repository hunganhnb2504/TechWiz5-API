package com.techwiz5.techwiz5.models.auth;

import lombok.Getter;

@Getter
public class ResetPasswordRequest {
    private String email;
    private String newPassword;
}
