package com.socialcommerce.feed.dto;

import com.socialcommerce.user.dto.UserForFeedResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FeedResponseDto {
    private UserForFeedResponseDto user;
    private UserForFeedResponseDto targetUser;
    private UserForFeedResponseDto actionUser;
    private ActivityForFeedResponseDto activity;

    // 포스팅 관련 정보
    private Long postId;
    private UserForFeedResponseDto postUser; // 포스팅한 사용자 정보
    private String postContent;
    private List<ActivityForFeedResponseDto> activities; // 포스팅에 대한 여러 활동 정보 (좋아요, 댓글 리스트)

}
