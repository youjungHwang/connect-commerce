package com.userservice.auth.controller;

import com.userservice.auth.dto.TokenDto;
import com.userservice.auth.dto.TokenRequestDto;
import com.userservice.auth.dto.LoginRequestDto;
import com.userservice.auth.dto.LoginResponseDto;
import com.userservice.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user-service")
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/api/v1/auth/signup")
    public ResponseEntity<LoginResponseDto> signUp(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.signUp(loginRequestDto));
    }

    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/api/v1/auth/refresh")
    public ResponseEntity<TokenDto> refreshToken(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.refresh(tokenRequestDto));
    }

    /**
     * 클라이언트로부터 받은 액세스 토큰을 사용하여 사용자의 인증 정보를 확인
     * 해당 사용자의 리프레시 토큰을 데이터베이스에서 삭제
     * -> 사용자가 로그아웃을 요청할 때 해당 사용자의 세션을 종료
     */
    @PostMapping("/api/v1/auth/logout")
    public ResponseEntity<Void> logout(@RequestBody TokenRequestDto tokenRequestDto) {
        authService.logout(tokenRequestDto);
        return ResponseEntity.ok().build();
    }

}
