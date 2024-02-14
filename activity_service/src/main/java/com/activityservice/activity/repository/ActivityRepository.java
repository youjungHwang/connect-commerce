package com.activityservice.activity.repository;

import com.activityservice.activity.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query("SELECT a FROM Activity a " +
            "LEFT JOIN FETCH a.targetUserId " +
            "LEFT JOIN FETCH a.actionUserId " +
            "WHERE a.targetUserId IN :followingIds " +
            "ORDER BY a.createdDate DESC")
    List<Activity> findActivitiesByFollowingIds(@Param("followingIds") List<Long> followingIds);

    @Query("SELECT a FROM Activity a WHERE a.actionUserId IN :followersIds ORDER BY a.createdDate DESC")
    List<Activity> findActivitiesByFollowersIds(@Param("followersIds") List<Long> followersIds);
}
