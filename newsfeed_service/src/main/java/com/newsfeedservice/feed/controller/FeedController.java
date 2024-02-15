//package com.newsfeedservice.feed.controller;
//
//import com.newsfeedservice.common.exception.HttpException;
//import com.newsfeedservice.feed.dto.FeedResponseDto;
//import com.newsfeedservice.feed.service.FeedService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Slf4j
//@RequiredArgsConstructor
//@RequestMapping("/newsfeed-service")
//@RestController
//public class FeedController {
//    private final FeedService feedService;
//
//    @GetMapping("/api/v1/feeds/followings-activities")
//    public ResponseEntity<List<FeedResponseDto>> getFollowingUsersActivities(@RequestParam(name = "member") final Long principalId)
//    {
//        try{
//            List<FeedResponseDto> feeds = feedService.getFollowingUsersActivities(principalId);
//            return new ResponseEntity<>(feeds, HttpStatus.OK);
//        }catch (Exception e){
//            throw new HttpException(
//                    false,
//                    "내가 팔로우한 유저들의 활동 피드 가져오기 실패",
//                    HttpStatus.INTERNAL_SERVER_ERROR
//            );
//        }
//    }
//
//    @GetMapping("/api/v1/feeds/my-posts-activities")
//    public ResponseEntity<List<FeedResponseDto>> getUserPostsActivities(@RequestParam(name = "member") final Long principalId){
//        try{
//            List<FeedResponseDto> feeds = feedService.getUserPostsActivities(principalId);
//            return new ResponseEntity<>(feeds, HttpStatus.OK);
//        }catch (Exception e){
//            throw new HttpException(
//                    false,
//                    "내 포스트의 상황(댓글, 좋아요) 가져오기 실패",
//                    HttpStatus.INTERNAL_SERVER_ERROR
//            );
//        }
//    }
//
//    @GetMapping("/api/v1/feeds/followers-activities")
//    public ResponseEntity<List<FeedResponseDto>> getFollowersActivities(@RequestParam(name = "member") final Long principalId){
//        try{
//            List<FeedResponseDto> feeds = feedService.getFollowersActivities(principalId);
//            return new ResponseEntity<>(feeds, HttpStatus.OK);
//        }catch (Exception e){
//            log.error("Error fetching followers activities 오류 내용 확인", e);
//            throw new HttpException(
//                    false,
//                    "나를 팔로우하는 유저들의 활동 피드 가져오기 실패",
//                    HttpStatus.INTERNAL_SERVER_ERROR
//            );
//        }
//    }
//
//}
