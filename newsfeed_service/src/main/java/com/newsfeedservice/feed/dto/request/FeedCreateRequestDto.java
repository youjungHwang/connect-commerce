package com.newsfeedservice.feed.dto.request;

import com.newsfeedservice.feed.entity.Feed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FeedCreateRequestDto {
    private Long userId;
    private Long targetUserId;
    private Feed.ActivityType activityType;
}
