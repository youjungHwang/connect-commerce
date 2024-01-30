package com.socialcommerce.user.dto;

import com.socialcommerce.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String email;

    public static LoginResponseDto loginResponseDto(User user) {
        return new LoginResponseDto(user.getEmail());
    }
}
