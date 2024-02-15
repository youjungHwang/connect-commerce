package com.activityservice.post.service;

import com.activityservice.post.dto.CreatePostRequestDto;
import com.activityservice.post.entity.Post;
import com.activityservice.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public void createPost(Long principalId, CreatePostRequestDto createPostRequestDto){
        Post post = Post.builder()
                .content(createPostRequestDto.getContent())
                .actionUserId(principalId)
                .build();

        postRepository.save(post);
    }
}
