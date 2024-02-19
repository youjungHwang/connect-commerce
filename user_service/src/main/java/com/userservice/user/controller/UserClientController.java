package com.userservice.user.controller;

import com.userservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RequestMapping("/user-service")
@RestController
public class UserClientController {
    private final UserService userService;

    @GetMapping("api/v1/users/{userId}/name")
    public ResponseEntity<String> getUserName(@PathVariable Long userId) {
        return userService.getUsername(userId)
                .map(username -> ResponseEntity.ok(username))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "유저 이름을 찾을 수 없습니다."));
    }


}
