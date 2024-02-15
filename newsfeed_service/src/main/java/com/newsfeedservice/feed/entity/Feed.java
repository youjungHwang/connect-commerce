package com.newsfeedservice.feed.entity;

import com.newsfeedservice.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Feed extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long id;

    // 활동을 수행한 사용자
    @Column(name = "user_id")
    private Long userId; //

    // 활동의 대상이 된 사용자
    @Column(name = "target_user_id", nullable = true)
    private Long targetUserId;

    @Column(name = "activity_id")
    private Long activityId;

}
