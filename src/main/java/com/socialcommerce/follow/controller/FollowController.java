package com.socialcommerce.follow.controller;

import com.socialcommerce.auth.CustomUserDetails;
import com.socialcommerce.dto.common.ResponseDto;
import com.socialcommerce.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow")
@RestController
public class FollowController { // + 실패의 경우
    private final FollowService followService;
    private static final int SUCCESS_CODE = 1;

    @PostMapping("/{toUserId}")
    public ResponseEntity<ResponseDto<?>> follow(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long toUserId) {
        followService.follow(customUserDetails.getUser().getId(), toUserId);
        log.info("FromUser ID {} toUser ID {}", customUserDetails.getUser().getId(), toUserId);
        return new ResponseEntity<>(new ResponseDto<>(SUCCESS_CODE, "팔로우 성공", null), HttpStatus.OK);
    }

    @DeleteMapping("/{toUserId}")
    public ResponseEntity<ResponseDto<?>> unFollow(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long toUserId) {
        followService.unFollow(customUserDetails.getUser().getId(), toUserId);
        return new ResponseEntity<>(new ResponseDto<>(SUCCESS_CODE, "언팔로우 성공", null), HttpStatus.OK);
    }
}
