package com.socialcommerce.follow;

import com.socialcommerce.feed.Activity;
import com.socialcommerce.feed.ActivityType;
import com.socialcommerce.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "follow_uq",
                        columnNames = {"from_user_id", "to_user_id"}
                )
        }
)
@DiscriminatorValue("FOLLOW")
@Entity
public class Follow extends Activity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="from_user_id")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="to_user_id")
    private User toUser;

    public Follow(User fromUser, User toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.FOLLOW;
    }

}

