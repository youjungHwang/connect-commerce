package com.socialcommerce.feed.dto;

import com.socialcommerce.user.dto.UserForFeedResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FeedResponseDto {
    private UserForFeedResponseDto user;
    private UserForFeedResponseDto targetUser;
    private ActivityForFeedResponseDto activity;
}
