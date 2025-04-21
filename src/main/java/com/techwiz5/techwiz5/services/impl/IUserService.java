
package com.techwiz5.techwiz5.services.impl;


import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.exceptions.AppException;
import com.techwiz5.techwiz5.exceptions.ErrorCode;
import com.techwiz5.techwiz5.repositories.UserRepository;
import com.techwiz5.techwiz5.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IUserService implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Optional<User> userOptional = userRepository.findByEmail(username);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    if ("user".equals(user.getUserType())) {
                        return userOptional.get();
                    }
                }
                throw new AppException(ErrorCode.INVALIDEMAILORPASSWORD);
            }
        };
    }
}
