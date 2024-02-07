package com.socialcommerce.follow.controller;

import com.socialcommerce.auth.CustomUserDetails;
import com.socialcommerce.dto.common.HttpException;
import com.socialcommerce.follow.service.FollowService;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow")
@RestController
public class FollowController {
    private final FollowService followService;
    private static final Logger logger = LoggerFactory.getLogger(FollowController.class);

    @PostMapping("/{toUserId}")
    public ResponseEntity<HttpException> follow(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long toUserId,
                                                 HttpServletRequest request) {
        String userInfo = customUserDetails.getUser().toString();
        Long userId = customUserDetails.getUser().getId();

        System.out.println("customUserDetails.getUser().toString(): " + userInfo);
        System.out.println("로그인 한 User ID를 확인: " + userId);

        Long fromUserId = customUserDetails.getUser().getId();
        if (fromUserId == null) {
            throw new IllegalStateException("fromUserId is null입니다.");
        }

        if (customUserDetails == null || customUserDetails.getUser() == null) {
            throw new IllegalStateException("CustomUserDetails or User is not properly set");
        }

        if(userId.equals(toUserId)){
            throw new DuplicateRequestException("자기 자신을 팔로우할 수 없습니다.");
        }

        followService.follow(fromUserId, toUserId);

        throw new HttpException(
                true,
                "팔로우 성공",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{toUserId}")
    public ResponseEntity<HttpException> unFollow(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long toUserId) {
        followService.unFollow(customUserDetails.getUser().getId(), toUserId);
        throw new HttpException(
                true,
                "팔로우 해제 성공",
                HttpStatus.OK
        );
    }
}
