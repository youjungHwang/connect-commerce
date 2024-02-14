package com.activityservice.comment.entity;

import com.activityservice.activity.entity.Activity;
import com.activityservice.activity.type.ActivityType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@DiscriminatorValue("COMMENT")
@Entity
public class Comment extends Activity {

    private String content;

    @Column(name = "post_id")
    private Long postId; // Post 엔티티 대신 Post ID 사용

    @Builder
    public Comment(Long id, Long actionUserId, Long targetUserId, String content, Long postId) {
        super(id, actionUserId, targetUserId);
        this.content = content;
        this.postId = postId;
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.COMMENT;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + getId() +
                ", actionUserId=" + (getActionUserId() != null ? getActionUserId() : "null") +
                ", targetUserId=" + (getTargetUserId() != null ? getTargetUserId() : "null") +
                ", content='" + content + '\'' +
                ", postId=" + postId +
                '}';
    }
}
