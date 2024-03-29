package com.socialcommerce.post.service;

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
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createPost(Long userId, CreatePostRequestDto createPostRequestDto){
        User user = userRepository.findById(userId).orElseThrow( ()-> new RuntimeException("해당 유저는 없습니다."));
        Post post = Post.builder()
                .content(createPostRequestDto.getContent())
                .actionUser(user)
                .build();

        postRepository.save(post);
    }
}
