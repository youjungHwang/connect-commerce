package com.socialcommerce.follow.service;

import com.socialcommerce.follow.Follow;
import com.socialcommerce.follow.FollowRepository;
import com.socialcommerce.user.User;
import com.socialcommerce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    @Transactional
    public void follow(Long fromUserId, Long toUserId) {
        User actionUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new UsernameNotFoundException("팔로우하는 사용자를 찾을 수 없습니다."));
        User targetUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new UsernameNotFoundException("팔로우되는 사용자를 찾을 수 없습니다."));

        // Follow 인스턴스 생성에 빌더 패턴 사용
        Follow follow = Follow.builder()
                .actionUser(actionUser)
                .targetUser(targetUser)
                .build();
        followRepository.save(follow);
    }
    @Transactional
    public void unFollow(Long fromUserId, Long toUserId) {
        User actionUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new UsernameNotFoundException("팔로우하는 사용자를 찾을 수 없습니다."));
        User targetUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new UsernameNotFoundException("팔로우되는 사용자를 찾을 수 없습니다."));

        followRepository.removeFollow(actionUser, targetUser);
    }
}
