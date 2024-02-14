package com.activityservice.likes.entity;

import com.activityservice.activity.entity.Activity;
import com.activityservice.activity.type.ActivityType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@DiscriminatorValue("LIKES")
@Entity
public class Likes extends Activity {
    @Column(name = "related_activity_id")
    private Long relatedActivityId; // Activity 엔티티 대신 관련 활동 ID 사용

    @Builder
    public Likes(Long id, Long actionUserId, Long targetUserId, Long relatedActivityId) {
        super(id, actionUserId, targetUserId);
        this.relatedActivityId = relatedActivityId;
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.LIKE;
    }

    @Override
    public String toString() {
        return "Likes{" +
                "id=" + getId() +
                ", actionUserId=" + (getActionUserId() != null ? getActionUserId() : "null") +
                ", targetUserId=" + (getTargetUserId() != null ? getTargetUserId() : "null") +
                ", relatedActivityId=" + relatedActivityId +
                '}';
    }


}
