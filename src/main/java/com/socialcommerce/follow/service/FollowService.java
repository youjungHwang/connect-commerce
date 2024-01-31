package com.socialcommerce.follow.service;

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
//    private User[] LongTypeToEntity(Long fromUserId, Long toUserId){
//        User fromUser = userRepository.findById(fromUserId)
//                .orElseThrow(() -> new UsernameNotFoundException("팔로우하는 유저를 찾을 수 없습니다."));
//        User toUser = userRepository.findById(toUserId)
//                .orElseThrow(() -> new UsernameNotFoundException("팔로우받는 유저를 찾을 수 없습니다."));
//        return new User[]{fromUser, toUser};
//    }
    @Transactional
    public void follow(Long fromUserId, Long toUserId) {
        if (fromUserId == null || toUserId == null) {
            log.error("follow 메소드 호출 시 fromUserId 또는 toUserId가 null입니다. fromUserId: {}, toUserId: {}", fromUserId, toUserId);
            throw new IllegalArgumentException("팔로우하려는 사용자 ID는 null일 수 없습니다.");
        }

        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new UsernameNotFoundException("팔로우하는 사용자를 찾을 수 없습니다."));
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new UsernameNotFoundException("팔로우되는 사용자를 찾을 수 없습니다."));

        followRepository.addFollow(fromUser, toUser);
    }
//    @Transactional
//    public void unFollow(Long fromUserId, Long toUserId) {
//        User[] users = LongTypeToEntity(fromUserId, toUserId);
//        followRepository.removeFollow(users[0], users[1]);
//    }
}
