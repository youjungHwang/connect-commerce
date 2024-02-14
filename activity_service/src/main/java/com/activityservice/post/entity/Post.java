package com.activityservice.post.entity;

import com.activityservice.activity.entity.Activity;
import com.activityservice.activity.type.ActivityType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@DiscriminatorValue("POST")
@Entity
public class Post extends Activity {

    @Column(nullable = false)
    private String content;

    // Comment와 Likes 엔티티와의 관계를 Post에서 직접 참조 방식 ->
    // Comment와 Likes에 Post ID를 저장해서 사용

    @Builder
    public Post(Long id, Long actionUserId, String content) {
        super(id, actionUserId);
        this.content = content;
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.POST;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + getId() +
                ", actionUserId=" + getActionUserId() +
                ", content='" + content + '\'' +
                '}';
    }




}
