package com.userservice.auth.service;

import com.userservice.auth.dto.TokenDto;
import com.userservice.auth.dto.TokenRequestDto;
import com.userservice.auth.security.CustomUserDetails;
import com.userservice.auth.security.RefreshToken;
import com.userservice.auth.security.RefreshTokenRepository;
import com.userservice.auth.security.TokenProvider;
import com.userservice.user.entity.User;
import com.userservice.user.repository.UserRepository;
import com.userservice.auth.dto.LoginRequestDto;
import com.userservice.auth.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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

    @Transactional
    public void logout(TokenRequestDto tokenRequestDto) {
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        refreshTokenRepository.findByKey(authentication.getName())
                .ifPresent(refreshToken -> {
                    refreshTokenRepository.delete(refreshToken);
                    log.debug("로그아웃 처리: 리프레시 토큰 삭제");
                });
    }

    /**
     * [activity-service] 통신을 위한 메서드
     */
    public Long getUserIdFromLogin(LoginRequestDto loginRequestDto) {
        TokenDto tokenDto = login(loginRequestDto);
        if (tokenDto == null) {
            throw new RuntimeException("로그인 실패");
        }
        Long userId = tokenProvider.getUserIdFromToken(tokenDto.getAccessToken());
        return userId;
    }


}
