package com.socialcommerce.feed.service;

import com.socialcommerce.comment.Comment;
import com.socialcommerce.feed.Activity;
import com.socialcommerce.feed.dto.ActivityForFeedResponseDto;
import com.socialcommerce.feed.dto.FeedResponseDto;
import com.socialcommerce.feed.repository.ActivityRepository;
import com.socialcommerce.likes.Likes;
import com.socialcommerce.post.Post;
import com.socialcommerce.post.PostRepository;
import com.socialcommerce.user.User;
import com.socialcommerce.user.UserRepository;
import com.socialcommerce.user.dto.UserForFeedResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedService {
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final PostRepository postRepository;

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

    @Transactional
    public List<FeedResponseDto> getUserPostsActivities(Long userId){
        List<Post> userPosts = postRepository.findAllByActionUserId(userId);

        List<FeedResponseDto> feedResponses = new ArrayList<>();
        for (Post post : userPosts) {
            UserForFeedResponseDto postUserDto = convertToUserDto(post.getActionUser());

            List<ActivityForFeedResponseDto> postActivitiesDto = new ArrayList<>();
            postActivitiesDto.addAll(convertCommentsToActivityDto(post.getComments()));
            postActivitiesDto.addAll(convertLikesToActivityDto(post.getLikes()));

            FeedResponseDto feedResponse = FeedResponseDto.builder()
                    .postId(post.getId())
                    .postUser(postUserDto)
                    .postContent(post.getContent())
                    .activities(postActivitiesDto)
                    .build();

            feedResponses.add(feedResponse);
        }

        return feedResponses;
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

    private List<ActivityForFeedResponseDto> convertCommentsToActivityDto(List<Comment> comments) {
        return comments.stream()
                .map(comment -> ActivityForFeedResponseDto.builder()
                        .id(comment.getId())
                        .activityType("COMMENT")
                        .content(comment.getContent())
                        .commentedBy(convertToUserDto(comment.getActionUser()))
                        .build())
                .collect(Collectors.toList());
    }

    private List<ActivityForFeedResponseDto> convertLikesToActivityDto(List<Likes> likes) {
        return likes.stream()
                .map(like -> ActivityForFeedResponseDto.builder()
                        .id(like.getId())
                        .activityType("LIKE")
                        .likedBy(convertToUserDto(like.getActionUser()))
                        .build())
                .collect(Collectors.toList());
    }




}
