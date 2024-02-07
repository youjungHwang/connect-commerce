package com.socialcommerce.feed;

import com.socialcommerce.BaseTimeEntity;
import com.socialcommerce.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_user_id")
    private User actionUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id", nullable = true)
    private User targetUser;

    public Activity(Long id, User actionUser) {
        super();
        this.id = id;
        this.actionUser = actionUser;
    }

    public Activity(Long id, User actionUser, User targetUser) {
        super();
        this.id = id;
        this.actionUser = actionUser;
        this.targetUser = targetUser;
    }

    public abstract ActivityType getActivityType();
}
