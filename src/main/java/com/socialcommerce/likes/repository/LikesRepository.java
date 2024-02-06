package com.socialcommerce.likes.repository;

import com.socialcommerce.likes.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
}
