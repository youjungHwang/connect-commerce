package com.socialcommerce.post;

import com.socialcommerce.comment.Comment;
import com.socialcommerce.feed.Activity;
import com.socialcommerce.feed.ActivityType;
import com.socialcommerce.likes.Likes;
import com.socialcommerce.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@DiscriminatorValue("POST")
@Entity
public class Post extends Activity {

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(Long id, User actionUser, String content, List<Comment> comments) {
        super(id, actionUser); // Activity 클래스에 있는 생성자를 호출
        this.content = content;
        this.comments = comments;
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.POST;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + getId() +
                ", actionUser=" + (getActionUser() != null ? getActionUser().getId() : "null") +
                ", content='" + content + '\'' +
                ", commentsCount=" + (comments != null ? comments.size() : 0) +
                '}';
    }


}
