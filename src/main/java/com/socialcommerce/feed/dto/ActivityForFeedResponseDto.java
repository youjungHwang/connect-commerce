package com.socialcommerce.feed.dto;

import com.socialcommerce.user.dto.UserForFeedResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ActivityForFeedResponseDto {
    private Long id;
    private String activityType;
    private String content; // 댓글 내용, 댓글인 경우에만 사용
    private UserForFeedResponseDto commentedBy; // 댓글을 작성한 사용자, 댓글인 경우에만 사용
    private UserForFeedResponseDto likedBy; // 좋아요 한 사용자, 좋아요인 경우에만 사용
}
