package com.newsfeedservice.feed.controller;

import com.newsfeedservice.common.exception.HttpException;
import com.newsfeedservice.feed.dto.request.FeedCreateRequestDto;
import com.newsfeedservice.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/newsfeed-service")
@RestController
public class FeedController {
    private final FeedService feedService;

    @PostMapping("/api/v1/newsfeed")
    public ResponseEntity<HttpException> createFeed(@RequestBody FeedCreateRequestDto feedCreateRequestDto) {
        feedService.createFeed(feedCreateRequestDto);
        throw new HttpException(
                true,
                "피드 생성 성공",
                HttpStatus.OK
        );
    }

    @GetMapping("/api/v1/newsfeed")
    public ResponseEntity<HttpException> getUserFeed(@RequestParam(name = "member") final Long principalId){
        feedService.getUserFeed(principalId);
        throw new HttpException(
                true,
                "로그인 유저의 피드 가져오기 성공",
                HttpStatus.OK
        );
    }
}
