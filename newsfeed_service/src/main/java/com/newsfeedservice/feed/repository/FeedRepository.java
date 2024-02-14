package com.newsfeedservice.feed.repository;


import com.newsfeedservice.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {
}
