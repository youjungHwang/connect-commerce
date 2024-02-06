package com.socialcommerce.user;

import com.socialcommerce.BaseTimeEntity;
import com.socialcommerce.comment.Comment;
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
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Feed> feeds = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();


    public void profileUpdate(String username, String profileImage, String greeting) {
        this.username = username;
        this.profileImage = profileImage;
        this.greeting = greeting;
    }

    public void passwordUpdate(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + (password != null ? "[PROTECTED]" : "null") + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", greeting='" + greeting + '\'' +
                ", isInfluencer=" + isInfluencer +
                '}';
    }

}

