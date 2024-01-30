package com.socialcommerce.user;

import com.socialcommerce.BaseTimeEntity;
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
    private String roles;
}

