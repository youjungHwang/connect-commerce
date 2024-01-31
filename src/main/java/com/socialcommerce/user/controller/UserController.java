package com.socialcommerce.user.controller;

import com.socialcommerce.auth.CustomUserDetails;
import com.socialcommerce.dto.common.HttpException;
import com.socialcommerce.user.dto.UserProfileUpdateDto;
import com.socialcommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    @PatchMapping("/{userId}")
    public ResponseEntity<HttpException> userProfileUpdate(@PathVariable Long userId, @AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserProfileUpdateDto userProfileUpdateDto){
        if(userId.equals(customUserDetails.getUser().getId())) {
            userService.userProfileUpdate(userId, userProfileUpdateDto);
            throw new HttpException(
                    true,
                    "유저 프로필 업데이트 성공",
                    HttpStatus.OK
            );
        }else {
            throw new HttpException(
                    false,
                    "유저 프로필 업데이트 권한 없음",
                    HttpStatus.FORBIDDEN
            );
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<HttpException> userPasswordUpdate(@PathVariable Long userId, @AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserProfileUpdateDto userProfileUpdateDto){
        if(userId.equals(customUserDetails.getUser().getId())) {
            userService.userPasswordUpdate(userId, userProfileUpdateDto);
            throw new HttpException(
                    true,
                    "유저 비밀번호 업데이트 성공",
                    HttpStatus.OK
            );
        }else {
            throw new HttpException(
                    false,
                    "유저 비밀번호 업데이트 권한 없음",
                    HttpStatus.FORBIDDEN
            );
        }
    }


}

