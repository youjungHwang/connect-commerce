package com.socialcommerce.comment;

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

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@DiscriminatorValue("COMMENT")
@Entity
public class Comment extends Activity {

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();;

    @Override
    public ActivityType getActivityType() {
        return ActivityType.COMMENT;
    }

}
