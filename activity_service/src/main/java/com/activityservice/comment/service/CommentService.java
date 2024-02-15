package com.activityservice.comment.service;

import com.activityservice.client.UserServiceClient;
import com.activityservice.comment.dto.CreateCommentRequestDto;
import com.activityservice.comment.entity.Comment;
import com.activityservice.comment.repository.CommentRepository;
import com.activityservice.post.entity.Post;
import com.activityservice.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {
    private final UserServiceClient userServiceClient;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void addCommentToPost(Long postId, CreateCommentRequestDto createCommentRequestDto, Long principalId) {
        if(principalId == null){
            throw new RuntimeException("사용자 정보를 조회할 수 없습니다.");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 포스트는 없습니다."));

        Comment comment = Comment.builder()
                .content(createCommentRequestDto.getContent())
                .postId(post.getId())
                .actionUserId(principalId) // 댓글 작성자 ID (로그인한 유저)
                .targetUserId(post.getActionUserId()) // Post의 작성자 ID를 targetUserId로 설정
                .build();
        commentRepository.save(comment);
    }

}
