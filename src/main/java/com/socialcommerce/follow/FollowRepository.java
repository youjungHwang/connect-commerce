package com.socialcommerce.follow;

import com.socialcommerce.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Logger log = LoggerFactory.getLogger(FollowRepository.class);

    boolean existsByFromUserAndToUser(User fromUser, User toUser);
    Optional<Follow> findByFromUserAndToUser(User fromUser, User toUser);

    default void addFollow(User fromUser, User toUser) {
        if (fromUser == null || toUser == null) {
            log.error("addFollow 메소드에서 fromUser 또는 toUser가 null입니다.");
            throw new IllegalArgumentException("fromUser와 toUser는 null일 수 없습니다.");
        }

        if (!existsByFromUserAndToUser(fromUser, toUser)) {
            save(new Follow(fromUser, toUser));
        }
    }

    default void removeFollow(User fromUser, User toUser) {
        if (fromUser == null || toUser == null) {
            log.error("removeFollow 메소드에서 fromUser 또는 toUser가 null입니다.");
            throw new IllegalArgumentException("fromUser와 toUser는 null일 수 없습니다.");
        }

        findByFromUserAndToUser(fromUser, toUser)
                .ifPresent(this::delete);
    }
}
