package com.socialcommerce.user.service;

import com.socialcommerce.user.User;
import com.socialcommerce.user.UserRepository;
import com.socialcommerce.user.dto.UserProfileUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void userProfileUpdate(Long userId, UserProfileUpdateRequestDto userProfileUpdateDto){
        User userEntity = userRepository.findById(userId).orElseThrow( ()-> new RuntimeException("해당 유저는 없습니다."));
        userEntity.profileUpdate(userProfileUpdateDto.getUsername(), userProfileUpdateDto.getProfileImage(), userProfileUpdateDto.getGreeting());
    }

    @Transactional
    public void userPasswordUpdate(Long userId, UserProfileUpdateRequestDto userProfileUpdateDto){
        User userEntity = userRepository.findById(userId).orElseThrow( ()-> new RuntimeException("해당 유저는 없습니다."));
        String encodedPassword = passwordEncoder.encode(userProfileUpdateDto.getPassword());
        userEntity.passwordUpdate(encodedPassword);
    }
}
