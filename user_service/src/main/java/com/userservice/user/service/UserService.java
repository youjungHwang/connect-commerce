package com.userservice.user.service;

import com.userservice.user.entity.User;
import com.userservice.user.repository.UserRepository;
import com.userservice.user.dto.UserProfileUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void userProfileUpdate(Long userId, UserProfileUpdateRequestDto userProfileUpdateDto){
        System.out.println(">>>>>>>>>>>> userProfileUpdate 들어옴");
        User userEntity = userRepository.findById(userId).orElseThrow( ()-> new RuntimeException("해당 유저는 없습니다."));
        log.debug("유저 프로필 업데이트- userEntity: {}", userEntity);
        userEntity.profileUpdate(userProfileUpdateDto.getUsername(), userProfileUpdateDto.getProfileImage(), userProfileUpdateDto.getGreeting());
    }

    @Transactional
    public void userPasswordUpdate(Long userId, UserProfileUpdateRequestDto userProfileUpdateDto){
        User userEntity = userRepository.findById(userId).orElseThrow( ()-> new RuntimeException("해당 유저는 없습니다."));
        String encodedPassword = passwordEncoder.encode(userProfileUpdateDto.getPassword());
        userEntity.passwordUpdate(encodedPassword);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long userId) {
        return userRepository.existsById(userId);
    }

}
