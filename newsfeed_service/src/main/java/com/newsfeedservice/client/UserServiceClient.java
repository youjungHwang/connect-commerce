package com.newsfeedservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-service")
public interface UserServiceClient {
// 호출 경로 /internal 내부 호출 - 이용하는거

}



