package com.newsfeedservice.feed.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FeedResponseDto {
    private Long userId;
    private Long targetUserId;
    private String activityType; // 활동 타입 ("COMMENT", "LIKE", "FOLLOW", "POST")
    private String details; // 활동 상세 내용

}
