package com.activityservice.post.controller;

import com.activityservice.comment.dto.CreateCommentRequestDto;
import com.activityservice.comment.service.CommentService;
import com.activityservice.common.exception.HttpException;
import com.activityservice.likes.service.LikesService;
import com.activityservice.post.dto.CreatePostRequestDto;
import com.activityservice.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/activity-service")
@RestController
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final LikesService likesService;

    /*
    * 포스트 API
    * */
    @PostMapping("/api/v1/posts")
    public ResponseEntity<HttpException> createPost(@RequestParam(name = "member") final Long principalId,
                                                    @RequestBody CreatePostRequestDto createPostRequestDto){

        System.out.println("컨트롤러");
        postService.createPost(principalId,createPostRequestDto);
        throw new HttpException(
                true,
                "포스트 작성 성공",
                HttpStatus.OK
        );
    }


    @PutMapping("/api/v1/posts/{postId}")
    public ResponseEntity<HttpException> updatePost(){
        throw new HttpException(
                true,
                "포스트 업데이트 성공",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/api/v1/posts/{postId}")
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
    @PostMapping("/api/v1/posts/{postId}/comments")
    public ResponseEntity<HttpException> addCommentToPost(@PathVariable Long postId,
                                                          @RequestBody CreateCommentRequestDto createCommentRequestDto,
                                                          @RequestParam(name = "member") final Long principalId) {
        // 사용자 ID 대신 토큰을 사용하여 댓글 추가
        commentService.addCommentToPost(postId, createCommentRequestDto, principalId);

        throw new HttpException(
                true,
                "포스트에 댓글 작성 성공",
                HttpStatus.OK
        );
    }

    @PutMapping("/api/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity<HttpException> updateCommentOnPost(){
        throw new HttpException(
                true,
                "포스트에 댓글 업데이트 성공",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/api/v1/posts/{postId}/comments/{commentId}")
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
    @PostMapping("/api/v1/posts/{postId}/likes")
    public ResponseEntity<HttpException> addLikesToPost(@RequestParam(name = "member") final Long principalId,
                                                        @PathVariable Long postId){

        likesService.addLikesToPost(principalId, postId);

        throw new HttpException(
                true,
                "포스트에 좋아요 추가 성공",
                HttpStatus.OK
        );
    }


}
