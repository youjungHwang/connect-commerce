package com.newsfeedservice.feed.entity;

import com.newsfeedservice.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class Feed extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "target_user_id", nullable = true)
    private Long targetUserId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ActivityType activityType;

    @Builder
    public Feed(Long userId, ActivityType activityType, Long targetUserId) {
        this.userId = userId;
        this.activityType = activityType;
        this.targetUserId = targetUserId;
    }

    public enum ActivityType {
        POST,
        COMMENT,
        FOLLOW,
        POST_LIKE,
        COMMENT_LIKE
    }

}
