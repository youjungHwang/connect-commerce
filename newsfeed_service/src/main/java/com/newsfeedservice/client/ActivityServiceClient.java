package com.newsfeedservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "activity-service")
public interface ActivityServiceClient {

}