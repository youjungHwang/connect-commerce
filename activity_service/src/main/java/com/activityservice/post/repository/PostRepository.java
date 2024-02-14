package com.activityservice.post.repository;

import com.activityservice.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByActionUserId(Long userId);
}
