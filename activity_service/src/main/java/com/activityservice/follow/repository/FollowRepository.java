package com.activityservice.follow.repository;

import com.activityservice.follow.entity.Follow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Logger log = LoggerFactory.getLogger(FollowRepository.class);

    Optional<Follow> findByActionUserIdAndTargetUserId(Long actionUserId, Long targetUserId);
    boolean existsByActionUserIdAndTargetUserId(Long actionUserId, Long targetUserId);

    default void removeFollow(Long actionUserId, Long targetUserId) {
        if (actionUserId == null || targetUserId == null) {
            log.error("removeFollow 메소드에서 actionUser 또는 targetUser가 null입니다.");
            throw new IllegalArgumentException("actionUser와 targetUser는 null일 수 없습니다.");
        }

        findByActionUserIdAndTargetUserId(actionUserId, targetUserId)
                .ifPresent(this::delete);
    }

    // 특정 사용자가 팔로우하는 사람들의 목록 조회
    @Query("SELECT f.targetUserId FROM Follow f WHERE f.actionUserId = :userId")
    List<Long> getFollowingUserIds(@Param("userId") Long userId);

    // 특정 사용자를 팔로우하는 사람들의 목록 조회
    @Query("SELECT f.actionUserId FROM Follow f WHERE f.targetUserId = :userId")
    List<Long> getFollowerUserIds(@Param("userId") Long userId);

}
