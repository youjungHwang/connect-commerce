package com.socialcommerce.follow;

import com.socialcommerce.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFromUserAndToUser(User fromUser, User toUser);
    Optional<Follow> findByFromUserAndToUser(User fromUser, User toUser);
    default void addFollow(User fromUser, User toUser) {
        if (!existsByFromUserAndToUser(fromUser, toUser)) {
            save(new Follow(fromUser, toUser));
        }
    }

    default void removeFollow(User fromUser, User toUser) {
        findByFromUserAndToUser(fromUser, toUser)
                .ifPresent(this::delete);
    }
}
