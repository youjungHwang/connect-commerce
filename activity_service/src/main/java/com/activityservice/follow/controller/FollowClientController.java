package com.activityservice.follow.controller;

import com.activityservice.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/activity-service")
@RestController
public class FollowClientController {
    private final FollowService followService;

    @GetMapping(value = "/api/v1/follow/followings/ids")
    public ResponseEntity<List<Long>> getFollowingUserIds(@RequestParam(name = "member") final Long principalId){
        List<Long> followingIds =  followService.getFollowingUserIds(principalId);
        return ResponseEntity.ok(followingIds);
    }
}
