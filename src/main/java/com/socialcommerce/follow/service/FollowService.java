package com.socialcommerce.follow.service;

import com.socialcommerce.follow.FollowRepository;
import com.socialcommerce.user.User;
import com.socialcommerce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private User[] LongTypeToEntity(Long fromUserId, Long toUserId){
        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new UsernameNotFoundException("팔로우하는 유저를 찾을 수 없습니다."));
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new UsernameNotFoundException("팔로우받는 유저를 찾을 수 없습니다."));
        return new User[]{fromUser, toUser};
    }
    @Transactional
    public void follow(Long fromUserId, Long toUserId) {
        try {
            User[] users = LongTypeToEntity(fromUserId, toUserId);
            followRepository.addFollow(users[0], users[1]);
        } catch (IllegalArgumentException e) {
            // [리팩토링] CustomException 구현
            System.out.println("이미 팔로우하고 있는 유저입니다.");
        }
    }
    @Transactional
    public void unFollow(Long fromUserId, Long toUserId) {
        User[] users = LongTypeToEntity(fromUserId, toUserId);
        followRepository.removeFollow(users[0], users[1]);
    }
}
