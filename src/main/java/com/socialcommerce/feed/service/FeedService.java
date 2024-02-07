package com.socialcommerce.feed.service;

import com.socialcommerce.feed.Activity;
import com.socialcommerce.feed.Feed;
import com.socialcommerce.feed.dto.ActivityForFeedResponseDto;
import com.socialcommerce.feed.dto.FeedResponseDto;
import com.socialcommerce.feed.repository.ActivityRepository;
import com.socialcommerce.feed.repository.FeedRepository;
import com.socialcommerce.post.Post;
import com.socialcommerce.post.dto.CreatePostRequestDto;
import com.socialcommerce.user.User;
import com.socialcommerce.user.UserRepository;
import com.socialcommerce.user.dto.UserForFeedResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedService {
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    @Transactional
    public List<FeedResponseDto> getFollowingUsersActivities(Long userId){
        List<Long> followingIds = userRepository.findFollowingIdsByUserId(userId);
        List<Activity> activities = activityRepository.findActivitiesByFollowingIds(followingIds);
        return activities.stream()
                .map(activity -> FeedResponseDto.builder()
                        .user(convertToUserDto(activity.getActionUser()))
                        .targetUser(convertToUserDto(activity.getTargetUser()))
                        .activity(convertToActivityDto(activity))
                        .build())
                .collect(Collectors.toList());
    }
    private UserForFeedResponseDto convertToUserDto(User user) {
        return UserForFeedResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .profileImage(user.getProfileImage())
                .greeting(user.getGreeting())
                .isInfluencer(user.getIsInfluencer())
                .build();
    }

    private ActivityForFeedResponseDto convertToActivityDto(Activity activity) {
        return ActivityForFeedResponseDto.builder()
                .id(activity.getId())
                .activityType(activity.getActivityType().name())
                .build();
    }

}
