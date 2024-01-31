package com.socialcommerce.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
* 1. 이름, 프로필 이미지, 인사말을 업데이트 할 수 있다.
* 2. 비밀번호를 업데이트 할 수 있다.
* */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileUpdateDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String profileImage;
    @NotBlank
    private String greeting;

    public UserProfileUpdateDto(String username, String profileImage, String greeting) {
        this.username = username;
        this.profileImage = profileImage;
        this.greeting = greeting;
    }

    public UserProfileUpdateDto(String password) {
        this.password = password;
    }
}
