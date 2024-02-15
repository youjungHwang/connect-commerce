package com.activityservice.comment.controller;

import com.activityservice.common.exception.HttpException;
import com.activityservice.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/activity-service")
@RestController
public class CommentController {

    private final LikesService likesService;

    @PostMapping("/api/v1/comments/{commentId}/likes")
    public ResponseEntity<HttpException> addLikesToComment(@RequestParam(name = "member") final Long principalId,
                                                           @PathVariable Long commentId){
        likesService.addLikesToComment(principalId, commentId);

        throw new HttpException(
                true,
                "댓글에 좋아요 추가 성공",
                HttpStatus.OK
        );
    }
}
