package com.socialcommerce.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.socialcommerce.BaseTimeEntity;
import com.socialcommerce.comment.Comment;
import com.socialcommerce.feed.Activity;
import com.socialcommerce.feed.Feed;
import com.socialcommerce.likes.Likes;
import com.socialcommerce.post.Post;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(unique=true)
    private String email;
    @Column(nullable = false)
    private String username;
    private String password;
    @Column(nullable = false)
    private String profileImage;
    @Column(nullable = false)
    private String greeting;
    private Boolean isInfluencer;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Feed> feeds = new ArrayList<>();

    // 사용자가 수행한 모든 활동
    @OneToMany(mappedBy = "actionUser")
    @JsonIgnore
    private List<Activity> activitiesPerformed;

    // 다른 사용자들이 해당 사용자를 대상으로 수행한 모든 활동
    @OneToMany(mappedBy = "targetUser")
    @JsonIgnore
    private List<Activity> activitiesTargeted;

    public void profileUpdate(String username, String profileImage, String greeting) {
        this.username = username;
        this.profileImage = profileImage;
        this.greeting = greeting;
    }

    public void passwordUpdate(String password) {
        this.password = password;
    }
}

