package com.helloegor03.friend_service.service;

import com.helloegor03.friend_service.config.JwtUtil;
import com.helloegor03.friend_service.model.Friend;
import com.helloegor03.friend_service.repository.FriendRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final JwtUtil jwtUtil;

    public FriendService(FriendRepository friendRepository, JwtUtil jwtUtil) {
        this.friendRepository = friendRepository;
        this.jwtUtil = jwtUtil;
    }

    public Friend addFriend(Friend friend, String token) {
        Long userId = jwtUtil.getUserIdFromToken(token);
        friend.setUserId(userId);

        boolean exists = friendRepository.findByUserIdAndFriendId(userId, friend.getFriendId()).isPresent();
        if (exists) {
            throw new RuntimeException("User is already your friend");
        }

        return friendRepository.save(friend);
    }

    public void deleteFriend(Long friendId, String token) {
        Long userId = jwtUtil.getUserIdFromToken(token);

        friendRepository.findByUserIdAndFriendId(userId, friendId)
                .ifPresentOrElse(
                        friendRepository::delete,
                        () -> { throw new RuntimeException("Cannot find this friend"); }
                );
    }

    public List<Friend> getAllFriends(String token) {
        Long userId = jwtUtil.getUserIdFromToken(token);
        return friendRepository.findAllByUserId(userId);
    }
}