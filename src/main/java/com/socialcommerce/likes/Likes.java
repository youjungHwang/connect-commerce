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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@DiscriminatorValue("LIKES")
@Entity
public class Likes extends Activity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Override
    public ActivityType getActivityType() {
        return ActivityType.LIKE;
    }

}
