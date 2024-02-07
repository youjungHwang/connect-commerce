package com.socialcommerce.comment.service;

import com.socialcommerce.comment.Comment;
import com.socialcommerce.comment.dto.CreateCommentRequestDto;
import com.socialcommerce.comment.repository.CommentRepository;
import com.socialcommerce.post.Post;
import com.socialcommerce.post.PostRepository;
import com.socialcommerce.post.dto.CreatePostRequestDto;
import com.socialcommerce.user.User;
import com.socialcommerce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void addCommentToPost(Long userId, Long postId ,CreateCommentRequestDto createCommentRequestDto){
        User actionUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저는 없습니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 포스트는 없습니다."));
        User targetUser = post.getActionUser(); // 댓글이 달릴 게시물(Post)의 작성자 -> Comment의 targetUser로 설정

        Comment comment = Comment.builder()
                .content(createCommentRequestDto.getContent())
                .post(post)
                .actionUser(actionUser)
                .targetUser(targetUser)
                .build();
        commentRepository.save(comment);
    }

}
