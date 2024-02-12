package com.userservice.user.entity;

import com.userservice.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

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

    public void profileUpdate(String username, String profileImage, String greeting) {
        this.username = username;
        this.profileImage = profileImage;
        this.greeting = greeting;
    }

    public void passwordUpdate(String password) {
        this.password = password;
    }
}

