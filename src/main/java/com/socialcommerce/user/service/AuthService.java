package com.socialcommerce.user.service;

import com.socialcommerce.jwt.TokenProvider;
import com.socialcommerce.jwt.RefreshToken;
import com.socialcommerce.jwt.RefreshTokenRepository;
import com.socialcommerce.user.User;
import com.socialcommerce.user.UserRepository;
import com.socialcommerce.user.dto.LoginRequestDto;
import com.socialcommerce.user.dto.LoginResponseDto;
import com.socialcommerce.jwt.dto.TokenDto;
import com.socialcommerce.jwt.dto.TokenRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    @Transactional
    public LoginResponseDto signUp(LoginRequestDto loginRequestDto) {
        if(userRepository.existsByEmail(loginRequestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        User user = loginRequestDto.toUser(passwordEncoder);
        User savedUser = userRepository.save(user);
        return LoginResponseDto.loginResponseDto(savedUser);
    }
    @Transactional
    public TokenDto login(LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();

        try {
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

            RefreshToken refreshToken = RefreshToken.builder()
                    .key(authentication.getName())
                    .value(tokenDto.getRefreshToken())
                    .build();

            refreshTokenRepository.save(refreshToken);

            return tokenDto;

        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    @Transactional
    public TokenDto refresh(TokenRequestDto tokenRequestDto) {
        if(!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
        }

        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return tokenDto;
    }
}
