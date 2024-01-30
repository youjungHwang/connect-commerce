package com.socialcommerce.user.controller;

import com.socialcommerce.user.UserRepository;
import com.socialcommerce.user.dto.LoginRequestDto;
import com.socialcommerce.user.dto.LoginResponseDto;
import com.socialcommerce.jwt.dto.TokenDto;
import com.socialcommerce.jwt.dto.TokenRequestDto;
import com.socialcommerce.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@RestController
public class AuthController {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
