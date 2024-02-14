//package com.newsfeedservice.feed.service;
//
//import com.newsfeedservice.feed.dto.ActivityForFeedResponseDto;
//import com.newsfeedservice.feed.dto.FeedResponseDto;
//import com.newsfeedservice.feed.dto.UserForFeedResponseDto;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class FeedService {
//    @Transactional
//    public List<FeedResponseDto> getFollowingUsersActivities(Long userId){
//        List<Long> followingIds = userRepository.findFollowingIdsByUserId(userId);
//        List<Activity> activities = activityRepository.findActivitiesByFollowingIds(followingIds);
//        return activities.stream()
//                .map(activity -> FeedResponseDto.builder()
//                        .user(convertToUserDto(activity.getActionUser()))
//                        .targetUser(convertToUserDto(activity.getTargetUser()))
//                        .activity(convertToBasicActivityDto(activity))
//                        .build())
//                .collect(Collectors.toList());
//    }
//
//    @Transactional
//    public List<FeedResponseDto> getUserPostsActivities(Long userId){
//        List<Post> userPosts = postRepository.findAllByActionUserId(userId);
//
//        List<FeedResponseDto> feedResponses = new ArrayList<>();
//        for (Post post : userPosts) {
//            UserForFeedResponseDto postUserDto = convertToUserDto(post.getActionUser());
//
//            List<ActivityForFeedResponseDto> postActivitiesDto = new ArrayList<>();
//            postActivitiesDto.addAll(convertCommentsToActivityDto(post.getComments()));
//            postActivitiesDto.addAll(convertLikesToActivityDto(post.getLikes()));
//
//            FeedResponseDto feedResponse = FeedResponseDto.builder()
//                    .postId(post.getId())
//                    .postUser(postUserDto)
//                    .postContent(post.getContent())
//                    .activities(postActivitiesDto)
//                    .build();
//
//            feedResponses.add(feedResponse);
//        }
//        return feedResponses;
//    }
//
//    @Transactional
//    public List<FeedResponseDto> getFollowersActivities(Long userId){
//        List<Long> followersIds = followRepository.findFollowerIdsByUserId(userId);
//        List<Activity> activities = activityRepository.findActivitiesByFollowersIds(followersIds);
//        return activities.stream()
//                .map(activity -> FeedResponseDto.builder()
//                        .user(convertToUserDto(activity.getActionUser()))
//                        .activity(convertToDetailedActivityDto(activity))
//                        .build())
//                .collect(Collectors.toList());
//    }
//
//    /*
//    *  헬퍼 메서드
//    * */
//    private UserForFeedResponseDto convertToUserDto(User user) {
//        return UserForFeedResponseDto.builder()
//                .id(user.getId())
//                .email(user.getEmail())
//                .username(user.getUsername())
//                .profileImage(user.getProfileImage())
//                .greeting(user.getGreeting())
//                .isInfluencer(user.getIsInfluencer())
//                .build();
//    }
//
//    // 기본 활동 정보를 DTO로 변환하는 메서드
//    private ActivityForFeedResponseDto convertToBasicActivityDto(Activity activity) {
//        return ActivityForFeedResponseDto.builder()
//                .id(activity.getId())
//                .activityType(activity.getActivityType().name())
//                .build();
//    }
//
//    private List<ActivityForFeedResponseDto> convertCommentsToActivityDto(List<Comment> comments) {
//        return comments.stream()
//                .map(comment -> ActivityForFeedResponseDto.builder()
//                        .id(comment.getId())
//                        .activityType("COMMENT")
//                        .content(comment.getContent())
//                        .commentedBy(convertToUserDto(comment.getActionUser()))
//                        .build())
//                .collect(Collectors.toList());
//    }
//
//    private List<ActivityForFeedResponseDto> convertLikesToActivityDto(List<Likes> likes) {
//        return likes.stream()
//                .map(like -> ActivityForFeedResponseDto.builder()
//                        .id(like.getId())
//                        .activityType("LIKE")
//                        .likedBy(convertToUserDto(like.getActionUser()))
//                        .build())
//                .collect(Collectors.toList());
//    }
//
//    // 상세 활동 정보를 DTO로 변환하는 메서드
//    private ActivityForFeedResponseDto convertToDetailedActivityDto(Activity activity) {
//        UserForFeedResponseDto userDto = convertToUserDto(activity.getActionUser());
//        switch (activity.getActivityType()) {
//            case COMMENT:
//                if (activity instanceof Comment) {
//                    Comment comment = (Comment) activity;
//                    return ActivityForFeedResponseDto.builder()
//                            .id(comment.getId())
//                            .activityType(comment.getActivityType().name())
//                            .content(comment.getContent())
//                            .commentedBy(userDto)
//                            .build();
//                }
//            case LIKE:
//                if (activity instanceof Likes) {
//                    Likes like = (Likes) activity;
//                    return ActivityForFeedResponseDto.builder()
//                            .id(like.getId())
//                            .activityType(like.getActivityType().name())
//                            .likedBy(userDto)
//                            .build();
//                }
//            case POST:
//                if (activity instanceof Post){
//                    Post post = (Post) activity;
//                    return ActivityForFeedResponseDto.builder()
//                            .id(post.getId())
//                            .activityType(post.getActivityType().name())
//                            .content(post.getContent())
//                            .build();
//                }
//
//            case FOLLOW:
//                return convertToBasicActivityDto(activity);
//            default:
//                return convertToBasicActivityDto(activity);
//        }
//    }
//
//}
