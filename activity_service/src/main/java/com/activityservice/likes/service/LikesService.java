//package com.activityservice.likes.service;
//
//import com.activityservice.client.UserServiceClient;
//import com.activityservice.client.dto.UserIdAndEmailFromJwtDto;
//import com.activityservice.comment.entity.Comment;
//import com.activityservice.comment.repository.CommentRepository;
//import com.activityservice.likes.entity.Likes;
//import com.activityservice.likes.repository.LikesRepository;
//import com.activityservice.post.entity.Post;
//import com.activityservice.post.repository.PostRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@RequiredArgsConstructor
//@Service
//public class LikesService {
//    private final UserServiceClient userServiceClient;
//    private final PostRepository postRepository;
//    private final LikesRepository likesRepository;
//    private final CommentRepository commentRepository;
//
//    @Transactional
//    public void addLikesToPost(String bearerToken, Long postId){
//        UserIdAndEmailFromJwtDto userInfo = userServiceClient.getUserIdAndEmailFromJwt(bearerToken);
//        Long userId = userInfo.getId();
//        if (userId == null) {
//            throw new RuntimeException("인증된 사용자 정보를 조회할 수 없습니다.");
//        }
//
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new RuntimeException("해당 포스트는 없습니다."));
//
//        Long targetUserId = post.getActionUserId();
//
//        Likes likes = Likes.builder()
//                .relatedActivityId(postId) // 관련 활동 ID로 postId를 사용
//                .actionUserId(userId) // 좋아요를 누른 사용자의 ID
//                .targetUserId(targetUserId) // 게시글 작성자의 ID
//                .build();
//
//        likesRepository.save(likes);
//    }
//
//    @Transactional
//    public void addLikesToComment(String bearerToken, Long commentId){
//        UserIdAndEmailFromJwtDto userInfo = userServiceClient.getUserIdAndEmailFromJwt(bearerToken);
//        Long userId = userInfo.getId();
//        if (userId == null) {
//            throw new RuntimeException("인증된 사용자 정보를 조회할 수 없습니다.");
//        }
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new RuntimeException("해당 댓글은 없습니다."));
//
//        Long targetUserId = comment.getActionUserId();
//
//        Likes likes = Likes.builder()
//                .relatedActivityId(commentId)
//                .actionUserId(userId)
//                .targetUserId(targetUserId)
//                .build();
//        likesRepository.save(likes);
//    }
//}
