package com.socialcommerce.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.socialcommerce.feed.Activity;
import com.socialcommerce.feed.ActivityType;
import com.socialcommerce.likes.Likes;
import com.socialcommerce.post.Post;
import com.socialcommerce.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;




@NoArgsConstructor
@Getter
@DiscriminatorValue("COMMENT")
@Entity
public class Comment extends Activity {

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    @Builder
    public Comment(Long id, User actionUser, User targetUser, String content, Post post) {
        super(id, actionUser, targetUser);
        this.content = content;
        this.post = post;
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.COMMENT;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + getId() +
                ", actionUser=" + (getActionUser() != null ? getActionUser().getId() : "null") +
                ", targetUser=" + (getTargetUser() != null ? getTargetUser().getId() : "null") +
                ", content='" + content + '\'' +
                ", post=" + (post != null ? post.getId() : "null") +
                '}';
    }


}
