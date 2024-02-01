package com.socialcommerce.post.controller;

import com.socialcommerce.auth.CustomUserDetails;
import com.socialcommerce.dto.common.HttpException;
import com.socialcommerce.post.dto.CreatePostRequestDto;
import com.socialcommerce.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<HttpException> createPost(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                    @RequestBody CreatePostRequestDto createPostRequestDto){

        postService.createPost(customUserDetails.getUser().getUserid(), createPostRequestDto);

        throw new HttpException(
                true,
                "포스트 작성 성공",
                HttpStatus.OK
        );
    }

    @PutMapping("/{postId}")
    public ResponseEntity<HttpException> updatePost(){
        throw new HttpException(
                true,
                "포스트 업데이트 성공",
                HttpStatus.OK
        );
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<HttpException> deletePost(){
        throw new HttpException(
                true,
                "포스트 삭제 성공",
                HttpStatus.OK
        );
    }

}
