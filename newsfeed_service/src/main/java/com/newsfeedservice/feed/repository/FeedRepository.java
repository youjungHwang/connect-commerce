package com.newsfeedservice.feed.repository;

import com.newsfeedservice.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    List<Feed> findByUserIdIn(List<Long> userIds, Sort sort);
}
