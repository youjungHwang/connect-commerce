package com.activityservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping(value = "/user-service/api/v1/users/{userId}")
    boolean existsById(@PathVariable("userId") Long userId);
}

