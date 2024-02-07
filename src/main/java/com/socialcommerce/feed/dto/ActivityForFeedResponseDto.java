package com.socialcommerce.feed.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ActivityForFeedResponseDto {
    private Long id;
    private String activityType;
}
