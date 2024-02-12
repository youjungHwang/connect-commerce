package com.userservice.auth.controller;

import com.userservice.auth.dto.TokenDto;
import com.userservice.auth.dto.TokenRequestDto;
import com.userservice.auth.dto.LoginRequestDto;
import com.userservice.auth.dto.LoginResponseDto;
import com.userservice.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<LoginResponseDto> signUp(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.signUp(loginRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> refreshToken(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.refresh(tokenRequestDto));
    }
}
