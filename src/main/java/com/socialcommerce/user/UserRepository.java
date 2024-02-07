package com.socialcommerce.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT f.targetUser.id FROM Follow f WHERE f.actionUser.id = :userId")
    List<Long> findFollowingIdsByUserId(@Param("userId") Long userId);

}

