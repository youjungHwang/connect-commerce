package com.socialcommerce.comment.repository;

import com.socialcommerce.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
