package com.socialcommerce.user.dto;

import com.socialcommerce.user.User;
import com.socialcommerce.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    private String email;
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

    // LoginRequestDto 클래스 내에 있는 toUser 메서드
    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .username("홍길동")
                .profileImage("기본이미지경로")
                .greeting("환영합니다!")
                .isInfluencer(false)
                .roles(String.valueOf(UserRole.ROLE_USER))
                .build();
    }
}
