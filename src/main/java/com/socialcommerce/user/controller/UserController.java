package com.socialcommerce.user.controller;

import com.socialcommerce.auth.CustomUserDetails;
import com.socialcommerce.dto.common.ResponseDto;
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
    private static final int SUCCESS_CODE = 1;
    private static final int FAILURE_CODE = -1;

    @PatchMapping("/{userId}")
    public ResponseEntity<ResponseDto<?>> userProfileUpdate(@PathVariable Long userId, @AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserProfileUpdateDto userProfileUpdateDto){
        if(userId.equals(customUserDetails.getUser().getId())) {
            userService.userProfileUpdate(userId, userProfileUpdateDto);
            return new ResponseEntity<>(new ResponseDto<>(SUCCESS_CODE, "유저 프로필 업데이트 성공", null), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResponseDto<>(FAILURE_CODE, "유저 프로필 업데이트 권한 없음", null), HttpStatus.FORBIDDEN);
        }
    }


}

