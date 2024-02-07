package com.socialcommerce.comment.controller;

import com.socialcommerce.auth.CustomUserDetails;
import com.socialcommerce.dto.common.HttpException;
import com.socialcommerce.likes.service.LikesService;
import com.socialcommerce.post.dto.CreatePostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@RestController
public class CommentController {

    private final LikesService likesService;

    @PostMapping("/{commentId}/likes")
    public ResponseEntity<HttpException> addLikesToComment(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                           @PathVariable Long commentId){
        likesService.addLikesToComment(customUserDetails.getUser().getId(), commentId);

        throw new HttpException(
                true,
                "댓글에 좋아요 추가 성공",
                HttpStatus.OK
        );
    }
}
