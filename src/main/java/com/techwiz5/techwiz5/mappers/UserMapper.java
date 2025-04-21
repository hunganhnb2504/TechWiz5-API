package com.techwiz5.techwiz5.mappers;


import com.techwiz5.techwiz5.dtos.UserDTO;
import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.exceptions.AppException;
import com.techwiz5.techwiz5.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toUserSummaryDTO(User model) {
        if (model == null) throw new AppException(ErrorCode.NOTFOUND);

        UserDTO userDTO = UserDTO.builder()
                .id(model.getId())
                .fullName(model.getFullName())
                .email(model.getEmail())
                .preferredCurrency(model.getPreferredCurrency())
                .profilePictureUrl(model.getProfilePictureUrl())
                .travelPreferences(model.getTravelPreferences())
                .build();
        return userDTO;
    }

}
