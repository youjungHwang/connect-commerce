package com.socialcommerce.follow;

import com.socialcommerce.feed.Activity;
import com.socialcommerce.feed.ActivityType;
import com.socialcommerce.user.User;
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
    public Follow(Long id, User actionUser, User targetUser) {
        super(id, actionUser, targetUser);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.FOLLOW;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "id=" + getId() +
                ", actionUser=" + (getActionUser() != null ? getActionUser().getId() : "null") +
                ", targetUser=" + (getTargetUser() != null ? getTargetUser().getId() : "null") +
                '}';
    }


}

