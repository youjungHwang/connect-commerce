package com.socialcommerce.follow.controller;

import com.socialcommerce.auth.CustomUserDetails;
import com.socialcommerce.dto.common.ResponseDto;
import com.socialcommerce.follow.service.FollowService;
import com.socialcommerce.user.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow")
@RestController
public class FollowController { // + 실패의 경우도 개발 해야 함
    private final FollowService followService;
    private static final int SUCCESS_CODE = 1;
    private static final int ERROR_CODE = -1;
    private static final Logger logger = LoggerFactory.getLogger(FollowController.class);



    @PostMapping("/{toUserId}")
    public ResponseEntity<ResponseDto<?>> follow(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long toUserId,
                                                 HttpServletRequest request) {
        User userExist = customUserDetails.getUser();
        Long userId = customUserDetails.getUser().getId();

        System.out.println("----------------------------");
        System.out.println("customUserDetails.getUser(): " + userExist);
        System.out.println("로그인 한 User ID를 확인: " + userId);
        System.out.println("----------------------------");

        Long fromUserId = customUserDetails.getUser().getId();
        if (fromUserId == null) {
            throw new IllegalStateException("fromUserId is null입니다.");
        }

        if (customUserDetails == null || customUserDetails.getUser() == null) {
            throw new IllegalStateException("CustomUserDetails or User is not properly set");
        }
        followService.follow(fromUserId, toUserId);
        return new ResponseEntity<>(new ResponseDto<>(SUCCESS_CODE, "팔로우 성공", null), HttpStatus.OK);
    }


//    @DeleteMapping("/{toUserId}")
//    public ResponseEntity<ResponseDto<?>> unFollow(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long toUserId) {
//        followService.unFollow(customUserDetails.getUser().getId(), toUserId);
//        return new ResponseEntity<>(new ResponseDto<>(SUCCESS_CODE, "언팔로우 성공", null), HttpStatus.OK);
//    }
}
