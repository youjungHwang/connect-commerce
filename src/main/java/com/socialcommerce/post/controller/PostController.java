package com.socialcommerce.post.controller;

import com.socialcommerce.auth.CustomUserDetails;
import com.socialcommerce.comment.dto.CreateCommentRequestDto;
import com.socialcommerce.comment.service.CommentService;
import com.socialcommerce.dto.common.HttpException;
import com.socialcommerce.likes.service.LikesService;
import com.socialcommerce.post.dto.CreatePostRequestDto;
import com.socialcommerce.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final LikesService likesService;

    /*
    * 포스트 API
    * */
    @PostMapping()
    public ResponseEntity<HttpException> createPost(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                    @RequestBody CreatePostRequestDto createPostRequestDto){

        postService.createPost(customUserDetails.getUser().getId(), createPostRequestDto);

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

    /*
     * 포스트 댓글 API
     * */
    @PostMapping("/{postId}/comments")
    public ResponseEntity<HttpException> addCommentToPost(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                          @PathVariable Long postId,
                                                          @RequestBody CreateCommentRequestDto createCommentRequestDto){

        commentService.addCommentToPost(customUserDetails.getUser().getId(), postId, createCommentRequestDto);

        throw new HttpException(
                true,
                "포스트에 댓글 작성 성공",
                HttpStatus.OK
        );
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<HttpException> updateCommentOnPost(){
        throw new HttpException(
                true,
                "포스트에 댓글 업데이트 성공",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<HttpException> removeCommentFromPost(){
        throw new HttpException(
                true,
                "포스트에 댓글 삭제 성공",
                HttpStatus.OK
        );
    }

    /*
     * 포스트 좋아요 API
     * */
    @PostMapping("/{postId}/likes")
    public ResponseEntity<HttpException> addLikesToPost(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                        @PathVariable Long postId){

        likesService.addLikesToPost(customUserDetails.getUser().getId(), postId);

        throw new HttpException(
                true,
                "포스트에 좋아요 추가 성공",
                HttpStatus.OK
        );
    }


}
