package com.activityservice.follow.controller;

import com.activityservice.common.exception.HttpException;
import com.activityservice.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/activity-service")
@RestController
public class FollowController {
    private final FollowService followService;

    @PostMapping("/api/v1/follow/{toUserId}")
    public ResponseEntity<HttpException> follow(@PathVariable Long toUserId,
                                                @RequestParam(name = "member") final Long principalId) {
        followService.follow(principalId, toUserId);

        throw new HttpException(
                true,
                "팔로우 성공",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/api/v1/follow/{toUserId}")
    public ResponseEntity<HttpException> unFollow(@RequestParam(name = "member") final Long principalId,
                                                  @PathVariable Long toUserId) {
        followService.unFollow(principalId, toUserId);
        throw new HttpException(
                true,
                "팔로우 해제 성공",
                HttpStatus.OK
        );
    }

}
