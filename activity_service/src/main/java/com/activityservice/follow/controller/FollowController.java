//package com.activityservice.follow.controller;
//
//import com.activityservice.common.exception.HttpException;
//import com.activityservice.follow.service.FollowService;
//import com.sun.jdi.request.DuplicateRequestException;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//
//@Slf4j
//@RequiredArgsConstructor
//@RequestMapping("/activity-service")
//@RestController
//public class FollowController {
//    private final FollowService followService;
//    private static final Logger logger = LoggerFactory.getLogger(FollowController.class);
//
//    @PostMapping("/api/v1/follow/{toUserId}")
//    public ResponseEntity<HttpException> follow(@RequestHeader("Authorization") String bearerToken,
//                                                @PathVariable Long toUserId,
//                                                HttpServletRequest request) {
//
//        followService.follow(bearerToken, toUserId);
//
//        throw new HttpException(
//                true,
//                "팔로우 성공",
//                HttpStatus.OK
//        );
//    }
//
//    @DeleteMapping("/api/v1/follow/{toUserId}")
//    public ResponseEntity<HttpException> unFollow(@RequestHeader("Authorization") String bearerToken,
//                                                  @PathVariable Long toUserId) {
//        followService.unFollow(bearerToken, toUserId);
//        throw new HttpException(
//                true,
//                "팔로우 해제 성공",
//                HttpStatus.OK
//        );
//    }
//}
