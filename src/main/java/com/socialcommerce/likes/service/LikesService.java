package com.socialcommerce.likes.service;

import com.socialcommerce.comment.Comment;
import com.socialcommerce.comment.dto.CreateCommentRequestDto;
import com.socialcommerce.comment.repository.CommentRepository;
import com.socialcommerce.likes.Likes;
import com.socialcommerce.likes.repository.LikesRepository;
import com.socialcommerce.post.Post;
import com.socialcommerce.post.PostRepository;
import com.socialcommerce.user.User;
import com.socialcommerce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikesRepository likesRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void addLikesToPost(Long userId, Long postId){
        User actionUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저는 없습니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 포스트는 없습니다."));
        User targetUser = post.getActionUser();

        Likes likes = Likes.builder()
                .relatedActivity(post)
                .actionUser(actionUser)
                .targetUser(targetUser)
                .build();
        likesRepository.save(likes);
    }

    @Transactional
    public void addLikesToComment(Long userId, Long commentId){
        User actionUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저는 없습니다."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("해당 댓글은 없습니다."));
        User targetUser = comment.getActionUser();

        Likes likes = Likes.builder()
                .relatedActivity(comment)
                .actionUser(actionUser)
                .targetUser(targetUser)
                .build();
        likesRepository.save(likes);
    }
}
