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
//    // 응답을 합치지 않고, 특정 사람마다 피드를 가져옴 (리스트 아이디 반환)
//    // 누가 누구에게 뭘했다.(타입) + 시간 아이디클릭, 요구사항문제
//
//    @Transactional
//    public List<FeedResponseDto> getFollowingUsersActivities(Long principalId){
//        //followingIds로 feedrepository호출 리스트 피드 가져오기 !! (feed, feedrepo 활용)
//        List<Long> followingIds = userRepository.findFollowingIdsByUserId(principalId);
//        // activityRepository로 가져올 필요가 없다.
//        List<Activity> activities = activityRepository.findActivitiesByFollowingIds(followingIds);
//        // activities 너무 많다면? -> 아이디만 뽑아서 가져오기. (리스트 or 페이지) + 최신 몇 개, 최대 몇 개 / 페이지 단위로
//        return activities.stream()
//                .map(activity -> FeedResponseDto.builder()
//                        .user(convertToUserDto(activity.getActionUserId()))
//                        .targetUser(convertToUserDto(activity.getTargetUserId()))
//                        .activity(convertToBasicActivityDto(activity))
//                        .build())
//                .collect(Collectors.toList());
//    }
//
//    @Transactional
//    public List<FeedResponseDto> getUserPostsActivities(Long userId){
//        // feedrepo사용해볼 것
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
