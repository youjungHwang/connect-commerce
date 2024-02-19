package com.newsfeedservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "activity-service")
public interface ActivityServiceClient {

    // 내가 팔로우한 유저Id 리스트
    @GetMapping(value = "/activity-service/api/v1/follow/followings/ids")
    List<Long> getFollowingUserIds(@RequestParam(name = "member") final Long principalId);
}
