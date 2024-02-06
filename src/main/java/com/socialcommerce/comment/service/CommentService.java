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
        User user = userRepository.findById(userId)
                .orElseThrow( ()-> new RuntimeException("해당 유저는 없습니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 포스트는 없습니다."));

        Comment comment = Comment.builder()
                .content(createCommentRequestDto.getContent())
                .post(post)
                .user(user)
                .build();
        commentRepository.save(comment);
    }

}
