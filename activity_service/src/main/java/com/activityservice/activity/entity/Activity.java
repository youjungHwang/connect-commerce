package com.activityservice.activity.entity;

import com.activityservice.activity.type.ActivityType;
import com.activityservice.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "activity_type", discriminatorType = DiscriminatorType.STRING)
@Entity
public abstract class Activity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long id;

    @Column(name = "action_user_id")
    private Long actionUserId;

    @Column(name = "target_user_id", nullable = true)
    private Long targetUserId;

    public Activity(Long id, Long actionUserId) {
        super();
        this.id = id;
        this.actionUserId = actionUserId;
    }

    public Activity(Long id, Long actionUserId, Long targetUserId) {
        super();
        this.id = id;
        this.actionUserId = actionUserId;
        this.targetUserId = targetUserId;
    }

    public abstract ActivityType getActivityType();
}
