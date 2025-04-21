package com.techwiz5.techwiz5.controllers;

import com.techwiz5.techwiz5.dtos.ResponseObject;
import com.techwiz5.techwiz5.dtos.UserDTO;
import com.techwiz5.techwiz5.dtos.auth.JwtAuthenticationResponse;
import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.exceptions.AppException;
import com.techwiz5.techwiz5.exceptions.ErrorCode;
import com.techwiz5.techwiz5.models.auth.*;
import com.techwiz5.techwiz5.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PutMapping("/user/profile")
    @Transactional
    public ResponseEntity<ResponseObject> updateProfile(
            @RequestParam("preferredCurrency") String preferredCurrency,
            @RequestParam("travelPreferences") List<String> travelPreferences,
            @RequestParam( "profilePictureUrl") MultipartFile profilePictureUrl) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth.getPrincipal() instanceof User)) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        User currentUser = (User) auth.getPrincipal();
        UpdateProfile updateProfile = new UpdateProfile();
        updateProfile.setPreferredCurrency(preferredCurrency);
        updateProfile.setTravelPreferences(travelPreferences);
        updateProfile.setProfilePictureUrl(profilePictureUrl);
        authenticationService.updateProfile(updateProfile, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }


    @PostMapping("/auth/user/signup")
    public ResponseEntity<ResponseObject> signup(@RequestBody @Valid SignUpRequest signUpRequest) {
        authenticationService.signup(signUpRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Successfully signed up!", "")
        );
    }

    @PostMapping("/auth/user/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.signin(signInRequest));
    }

    @PostMapping("/auth/user/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }


    @GetMapping("/user/profile")
    ResponseEntity<ResponseObject> profile() {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(auth.getPrincipal() instanceof User)) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        if (auth == null) throw new AppException(ErrorCode.UNAUTHENTICATED);
        User currentUser = (User) auth.getPrincipal();
        UserDTO userDTO = authenticationService.profile(currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", userDTO)
        );
    }
    @PostMapping("/user/change-password")
    public ResponseEntity<ResponseObject> changepassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(auth.getPrincipal() instanceof User)) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        if (auth == null) throw new AppException(ErrorCode.UNAUTHENTICATED);
        User currentUser = (User) auth.getPrincipal();
        authenticationService.changePassword(changePasswordRequest, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }

    @PostMapping("/auth/user/forgot-password")
    public ResponseEntity<ResponseObject> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        authenticationService.forgotPassword(forgotPasswordRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }

    @PostMapping("/auth/user/reset-password/{token}")
    public ResponseEntity<ResponseObject> resetPassword(
            @PathVariable("token") String token,
            @RequestBody ResetPasswordRequest resetPasswordRequest
    ) {
        authenticationService.resetPassword(resetPasswordRequest, token);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }

}