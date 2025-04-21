package com.techwiz5.techwiz5.services;


import com.techwiz5.techwiz5.dtos.UserDTO;
import com.techwiz5.techwiz5.dtos.auth.JwtAuthenticationResponse;
import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.models.auth.*;

public interface AuthenticationService  {

    void signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    UserDTO profile(User currentUser);
    void changePassword(ChangePasswordRequest changePasswordRequest, User user);
    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
    void resetPassword(ResetPasswordRequest resetPasswordRequest, String token);
    void updateProfile(UpdateProfile updateProfile, User user);
}