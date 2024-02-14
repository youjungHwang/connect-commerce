package com.activityservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "user-service")
public interface UserServiceClient {

//    @GetMapping("/api/v1/users/my-info")
//    UserIdAndEmailFromJwtDto getUserIdAndEmailFromJwt(@RequestHeader("Authorization") String bearerToken);

//    @PostMapping("/user-service/api/v1/auth/my-info")
//    Long userId(@RequestBody LoginRequestDto loginRequestDto);

    @GetMapping("/user-service/api/v1/auth/user-email") // 다시 수정
    String getUserEmailFromLogin();

    @GetMapping("/user-service/api/v1/auth/user-id") // 다시 수정
    Long getUserIdFromLogin();

    @GetMapping("/user-service/api/v1/users/{userId}")
    boolean existsById(@PathVariable("userId") Long userId);

}



