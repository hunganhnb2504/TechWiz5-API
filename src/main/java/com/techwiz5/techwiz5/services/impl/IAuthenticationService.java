package com.techwiz5.techwiz5.services.impl;


import com.techwiz5.techwiz5.dtos.UserDTO;
import com.techwiz5.techwiz5.dtos.auth.JwtAuthenticationResponse;
import com.techwiz5.techwiz5.entities.Role;
import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.exceptions.AppException;
import com.techwiz5.techwiz5.exceptions.ErrorCode;
import com.techwiz5.techwiz5.mappers.UserMapper;
import com.techwiz5.techwiz5.models.auth.*;
import com.techwiz5.techwiz5.models.mail.MailStructure;
import com.techwiz5.techwiz5.repositories.UserRepository;
import com.techwiz5.techwiz5.services.AuthenticationService;
import com.techwiz5.techwiz5.services.JWTService;
import com.techwiz5.techwiz5.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class IAuthenticationService implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final MailService mailService;
    private final StorageService storageService;
    private final UserMapper userMapper;

    private static final String ALLOWED_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            sb.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }


    private JwtAuthenticationResponse generateJwtToken(User user) {
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(token);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }




    @Override
    public void signup(SignUpRequest signUpRequest) {
        Optional<User> userExiting = userRepository.findByEmail(signUpRequest.getEmail());
        if (userExiting.isPresent()) {
        } else {
            User user = new User();
            user.setFullName(signUpRequest.getFullname());
            user.setEmail(signUpRequest.getEmail());
            user.setFullName(signUpRequest.getFullname());
            user.setRole(Role.USER);
            user.setUserType("user");
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            userRepository.save(user);
        }
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest signInRequest) {
        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new AppException(ErrorCode.INVALIDEMAILORPASSWORD));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                signInRequest.getPassword()));

        var jwt = jwtService.generateToken(user);

        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String useEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(useEmail).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            var jwt = jwtService.generateToken(user);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }

    @Override
    public UserDTO profile(User currentUser) {
        if (currentUser == null) throw new AppException(ErrorCode.NOTFOUND);
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        return userMapper.toUserSummaryDTO(user);
    }



    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest, User user) {
        System.out.println(changePasswordRequest.toString());
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect current password");
        }
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())){
            throw new RuntimeException("Incorrect current password");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        User user = userRepository.findByEmail(forgotPasswordRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));
        String resetToken = generateResetToken();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        user.setResetToken(resetToken);
        user.setResetTokenExpiry(new java.sql.Date(calendar.getTime().getTime()));
        userRepository.save(user);


        String resetLink = "http://localhost:3001/reset-password/" + resetToken;

        MailStructure mailStructure = new MailStructure();
        mailStructure.setSubject("Password Reset");
        mailStructure.setMessage("Click the link to reset your password: " + resetLink);
        mailService.sendMail(forgotPasswordRequest.getEmail(), mailStructure);
    }

    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest, String token) {
        User user = userRepository.findByEmail(resetPasswordRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));
        if (user.getResetToken() == null || !user.getResetToken().equals(token)) {
            throw new AppException(ErrorCode.INVALID_RESETTOKEN);
        }
        Date now = new Date();
        if (user.getResetTokenExpiry() == null || user.getResetTokenExpiry().before(now)) {
            throw new AppException(ErrorCode.INVALID_RESETTOKEN);
        }

        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void updateProfile(UpdateProfile updateProfile, User user) {
        if (updateProfile.getPreferredCurrency() != null && !updateProfile.getPreferredCurrency().isEmpty()) {
            user.setPreferredCurrency(updateProfile.getPreferredCurrency());
        }
        if (updateProfile.getTravelPreferences() != null) {
            user.getTravelPreferences().clear();
            user.getTravelPreferences().addAll(updateProfile.getTravelPreferences());
        }
        if (updateProfile.getProfilePictureUrl() != null && !updateProfile.getProfilePictureUrl().isEmpty()) {
            String generatedFileName = storageService.storeFile(updateProfile.getProfilePictureUrl());
            user.setProfilePictureUrl("http://localhost:8080/api/v1/FileUpload/files/"+ generatedFileName);
        }
        userRepository.save(user);
    }

    private String generateResetToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
