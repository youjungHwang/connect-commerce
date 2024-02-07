package com.socialcommerce.feed.repository;

import com.socialcommerce.feed.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {
}
