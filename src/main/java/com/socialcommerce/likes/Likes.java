package com.socialcommerce.likes;

import com.socialcommerce.comment.Comment;
import com.socialcommerce.feed.Activity;
import com.socialcommerce.feed.ActivityType;
import com.socialcommerce.post.Post;
import com.socialcommerce.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@DiscriminatorValue("LIKES")
@Entity
public class Likes extends Activity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_id")
    private Activity relatedActivity;

    @Builder
    public Likes(Long id, User actionUser, User targetUser, Activity relatedActivity) {
        super(id, actionUser, targetUser);
        this.relatedActivity = relatedActivity;
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.LIKE;
    }

    @Override
    public String toString() {
        return "Likes{" +
                "id=" + getId() +
                ", actionUser=" + (getActionUser() != null ? getActionUser().getId() : "null") +
                ", targetUser=" + (getTargetUser() != null ? getTargetUser().getId() : "null") +
                ", relatedActivity=" + (relatedActivity != null ? relatedActivity.getId() : "null") +
                '}';
    }


}
