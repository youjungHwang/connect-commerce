package com.socialcommerce.feed.controller;

import com.socialcommerce.auth.CustomUserDetails;
import com.socialcommerce.dto.common.HttpException;
import com.socialcommerce.feed.dto.FeedResponseDto;
import com.socialcommerce.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/feeds")
@RestController
public class FeedController {
    private final FeedService feedService;

    @GetMapping("/followings-activities")
    public ResponseEntity<List<FeedResponseDto>> getFollowingUsersActivities(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        try{
            List<FeedResponseDto> feeds = feedService.getFollowingUsersActivities(customUserDetails.getUser().getId());
            return new ResponseEntity<>(feeds, HttpStatus.OK);
        }catch (Exception e){
            throw new HttpException(
                    false,
                    "내가 팔로우한 유저들의 활동 피드 가져오기 실패",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/my-posts-activities")
    public ResponseEntity<List<FeedResponseDto>> getUserPostsActivities(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        try{
            List<FeedResponseDto> feeds = feedService.getUserPostsActivities(customUserDetails.getUser().getId());
            return new ResponseEntity<>(feeds, HttpStatus.OK);
        }catch (Exception e){
            throw new HttpException(
                    false,
                    "내 포스트의 상황(댓글, 좋아요) 가져오기 실패",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/followers-activities")
    public ResponseEntity<List<FeedResponseDto>> getFollowersActivities(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        try{
            List<FeedResponseDto> feeds = feedService.getFollowersActivities(customUserDetails.getUser().getId());
            return new ResponseEntity<>(feeds, HttpStatus.OK);
        }catch (Exception e){
            log.error("Error fetching followers activities 오류 내용 확인", e);
            throw new HttpException(
                    false,
                    "나를 팔로우하는 유저들의 활동 피드 가져오기 실패",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}
