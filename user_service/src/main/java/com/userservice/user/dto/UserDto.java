package com.userservice.user.dto;

import lombok.*;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String username;
    private String password;

    private Date createdAt;

    private String decryptedPwd;
    private String encryptedPwd;
}

