package com.newsfeedservice.client;

import com.newsfeedservice.client.dto.UserIdAndEmailFromJwtDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "user-service")
public interface UserServiceClient {


}



