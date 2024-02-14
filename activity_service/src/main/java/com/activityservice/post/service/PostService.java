package com.activityservice.post.service;

import com.activityservice.client.UserServiceClient;
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
    private final UserServiceClient userServiceClient;


    @Transactional
    public void createPost(CreatePostRequestDto createPostRequestDto){
        String email = userServiceClient.getUserEmailFromLogin();
        Long principalId = userServiceClient.getUserIdFromLogin();

        if (email == null) {
            throw new RuntimeException("[user-service]에서 인증된 사용자 이메일 정보를 가져올 수 없습니다.");
        }

        Post post = Post.builder()
                .content(createPostRequestDto.getContent())
                .actionUserId(principalId)
                .build();

        postRepository.save(post);
    }
}
