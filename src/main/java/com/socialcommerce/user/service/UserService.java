package com.socialcommerce.user.service;

import com.socialcommerce.user.User;
import com.socialcommerce.user.UserRepository;
import com.socialcommerce.user.dto.UserProfileUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void userProfileUpdate(Long userId,UserProfileUpdateDto userProfileUpdateDto){
        User userEntity = userRepository.findById(userId).orElseThrow( ()-> new RuntimeException("해당 유저는 없습니다."));
        userEntity.profileUpdate(userProfileUpdateDto.getUsername(), userProfileUpdateDto.getProfileImage(), userProfileUpdateDto.getGreeting());
    }
}
