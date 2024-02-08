package com.socialcommerce.follow;

import com.socialcommerce.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Logger log = LoggerFactory.getLogger(FollowRepository.class);

    boolean existsByActionUserAndTargetUser(User actionUser, User targetUser);
    Optional<Follow> findByActionUserAndTargetUser(User actionUser, User targetUser);

    default void addFollow(User actionUser, User targetUser) {
        if (actionUser == null || targetUser == null) {
            log.error("addFollow 메소드에서 actionUser 또는 targetUser가 null입니다.");
            throw new IllegalArgumentException("actionUser와 targetUser는 null일 수 없습니다.");
        }

        if (!existsByActionUserAndTargetUser(actionUser, targetUser)) {
            save(new Follow(null, actionUser, targetUser));
        }
    }

    default void removeFollow(User actionUser, User targetUser) {
        if (actionUser == null || targetUser == null) {
            log.error("removeFollow 메소드에서 actionUser 또는 targetUser가 null입니다.");
            throw new IllegalArgumentException("actionUser와 targetUser는 null일 수 없습니다.");
        }

        findByActionUserAndTargetUser(actionUser, targetUser)
                .ifPresent(this::delete);
    }

    @Query("SELECT f.actionUser.id FROM Follow f WHERE f.targetUser.id = :userId")
    List<Long> findFollowerIdsByUserId(@Param("userId") Long userId);
}
