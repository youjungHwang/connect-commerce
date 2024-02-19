package com.activityservice.follow.service;

import com.activityservice.client.UserServiceClient;
import com.activityservice.follow.entity.Follow;
import com.activityservice.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final UserServiceClient userServiceClient;

    @Transactional
    public void follow(Long principalId, Long toUserId) {
        if(principalId == null){
            throw new RuntimeException("사용자 정보를 조회할 수 없습니다.");
        }

        // 팔로우하려는 대상 사용자가 존재하는지 확인
        boolean userExists = userServiceClient.existsById(toUserId);
        if (!userExists) {
            throw new RuntimeException("팔로우하려는 사용자가 존재하지 않습니다.");
        }

        // 자기 자신을 팔로우하는 경우를 확인
        if (principalId.equals(toUserId)) {
            throw new RuntimeException("자기 자신을 팔로우할 수 없습니다.");
        }

        // 팔로우 관계가 이미 존재하는지 확인
        boolean exists = followRepository.existsByActionUserIdAndTargetUserId(principalId, toUserId);
        if (exists) {
            log.info("이미 팔로우 관계가 존재합니다.");
            throw new RuntimeException("이미 팔로우 관계가 존재합니다.");
        }

        Follow follow = Follow.builder()
                .actionUserId(principalId)
                .targetUserId(toUserId)
                .build();
        followRepository.save(follow);
    }
    @Transactional
    public void unFollow(Long principalId, Long toUserId) {
        if (principalId == null) {
            throw new RuntimeException("사용자 정보를 조회할 수 없습니다.");
        }

        boolean exists = followRepository.existsByActionUserIdAndTargetUserId(principalId, toUserId);
        if (!exists) {
            log.info("팔로우 관계가 존재하지 않습니다.");
            throw new RuntimeException("팔로우 관계가 존재하지 않습니다.");
        }

        // 팔로우 관계를 삭제
        followRepository.removeFollow(principalId, toUserId);
    }

    public List<Long> getFollowingUserIds(Long principalId) {
        return followRepository.getFollowingUserIds(principalId);
    }


}
