package com.userservice.auth.controller;


import com.userservice.auth.dto.TokenDto;
import com.userservice.auth.dto.TokenRequestDto;
import com.userservice.auth.dto.LoginRequestDto;
import com.userservice.auth.dto.LoginResponseDto;
import com.userservice.auth.security.CustomUserDetails;
import com.userservice.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    /**
     * [activity-service] 통신을 위한 메서드 -> 정상 동작
     */
    @GetMapping("/api/v1/auth/user-email")
    public ResponseEntity<String> getUserEmailFromLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String email = userDetails.getUser().getEmail();
            return ResponseEntity.ok(email);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
    }

    @GetMapping("/api/v1/auth/user-id")
    public ResponseEntity<Long> getUserIdFromLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getUser().getId();
            return ResponseEntity.ok(userId);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

}
