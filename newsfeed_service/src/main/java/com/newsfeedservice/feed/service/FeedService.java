package com.newsfeedservice.feed.service;

import com.newsfeedservice.client.ActivityServiceClient;
import com.newsfeedservice.client.UserServiceClient;
import com.newsfeedservice.feed.dto.request.FeedCreateRequestDto;
import com.newsfeedservice.feed.dto.response.ActivityDetailsDto;
import com.newsfeedservice.feed.dto.response.FeedResponseDto;
import com.newsfeedservice.feed.entity.Feed;
import com.newsfeedservice.feed.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedService {

    private final UserServiceClient userServiceClient;
    private final ActivityServiceClient activityServiceClient;
    private final FeedRepository feedRepository;

    public void createFeed(FeedCreateRequestDto feedCreateRequestDto) {
        Feed newsfeed = Feed.builder()
                .userId(feedCreateRequestDto.getUserId())
                .activityType(feedCreateRequestDto.getActivityType())
                .targetUserId(feedCreateRequestDto.getTargetUserId())
                .build();

        feedRepository.save(newsfeed);
    }

    public List<FeedResponseDto> getUserFeed(Long principalId) {
        log.info("진입");
        List<Long> followingIds = activityServiceClient.getFollowingUserIds(principalId);
        log.info("아이디 followingIds:{}", followingIds);
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        return feedRepository.findByUserIdIn(followingIds, sort).stream()
                .map(feed -> {
                    String details = ActivityDetailsDto.formatActivityDetails(
                            userServiceClient,
                            feed.getUserId(),
                            feed.getTargetUserId(),
                            feed.getActivityType()
                    );
                    return new FeedResponseDto(
                            feed.getUserId(),
                            feed.getTargetUserId(),
                            feed.getActivityType().toString(),
                            details
                    );
                }).collect(Collectors.toList());
    }

}
