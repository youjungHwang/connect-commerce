package com.userservice.user.controller;

import com.userservice.auth.security.CustomUserDetails;
import com.userservice.common.exception.HttpException;
import com.userservice.user.dto.UserProfileUpdateRequestDto;
import com.userservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/user-service")
@RestController
public class UserController {

    private final UserService userService;

    @PatchMapping("/api/v1/users/{userId}")
    public ResponseEntity<HttpException> userProfileUpdate(@PathVariable Long userId,
                                                           @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                           @RequestBody UserProfileUpdateRequestDto userProfileUpdateDto){
        System.out.println(">>>>>>> UserController 들어옴 ");
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

    @PutMapping("/api/v1/users/{userId}")
    public ResponseEntity<HttpException> userPasswordUpdate(@PathVariable Long userId,
                                                            @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                            @RequestBody UserProfileUpdateRequestDto userProfileUpdateDto){
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

    /**
     * [activity-service] 통신을 위한 메서드
     */
    @GetMapping("/api/v1/users/{userId}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long userId) {
        boolean exists = userService.existsById(userId);
        System.out.println("아이디에 해당하는 유저 존재여부 :" + exists);
        return ResponseEntity.ok(exists);
    }


}

