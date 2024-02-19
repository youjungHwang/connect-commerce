package com.newsfeedservice.feed.dto.response;

import com.newsfeedservice.client.UserServiceClient;
import com.newsfeedservice.feed.entity.Feed;

public class ActivityDetailsDto {
    public static String formatActivityDetails(UserServiceClient userServiceClient, Long userId, Long targetUserId, Feed.ActivityType activityType) {
        String userName = userServiceClient.getUserName(userId);
        switch (activityType) {
            case POST -> {
                return userName + "님이 새 글을 작성했습니다.";
            }
            case COMMENT -> {
                return userName + "님이 " + targetUserId + "번 글에 댓글을 남겼습니다.";
            }
            case POST_LIKE -> {
                return userName + "님이 " + targetUserId + "번 글에 좋아요를 눌렀습니다.";
            }
            case COMMENT_LIKE -> {
                return userName + "님이 " + targetUserId + "번 댓글에 좋아요를 눌렀습니다.";
            }
            case FOLLOW -> {
                String followingName = userServiceClient.getUserName(targetUserId);
                return userName + "님이 " + followingName + "님을 팔로우했습니다.";
            }
            default -> {
                return "활동 없음";
            }
        }
    }
}
