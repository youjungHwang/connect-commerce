package com.activityservice.follow.entity;

import com.activityservice.activity.entity.Activity;
import com.activityservice.activity.type.ActivityType;
import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "follow_uq",
                        columnNames = {"action_user_id", "target_user_id"}
                )
        }
)
@DiscriminatorValue("FOLLOW")
@Entity
public class Follow extends Activity {
    @Builder
    public Follow(Long id, Long actionUserId, Long targetUserId) {
        // Activity 기본 생성자를 호출하여 사용자 ID를 설정
        super(id, actionUserId, targetUserId);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.FOLLOW;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "id=" + getId() +
                ", actionUserId=" + (getActionUserId() != null ? getActionUserId() : "null") +
                ", targetUserId=" + (getTargetUserId() != null ? getTargetUserId() : "null") +
                '}';
    }

}

