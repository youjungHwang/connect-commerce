package com.socialcommerce.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileUpdateRequestDto {
    private String username;
    private String password;
    private String profileImage;
    private String greeting;

    public UserProfileUpdateRequestDto(String username, String profileImage, String greeting) {
        this.username = username;
        this.profileImage = profileImage;
        this.greeting = greeting;
    }

    public UserProfileUpdateRequestDto(String password) {
        this.password = password;
    }
}
