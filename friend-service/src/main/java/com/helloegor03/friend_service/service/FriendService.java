package com.helloegor03.friend_service.service;

import com.helloegor03.friend_service.config.JwtUtil;
import com.helloegor03.friend_service.dto.FriendDto;
import com.helloegor03.friend_service.model.Friend;
import com.helloegor03.friend_service.model.UserCache;
import com.helloegor03.friend_service.repository.FriendRepository;
import com.helloegor03.friend_service.repository.UserCacheRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserCacheRepository userCacheRepository;
    private final JwtUtil jwtUtil;

    public FriendService(FriendRepository friendRepository, UserCacheRepository userCacheRepository, JwtUtil jwtUtil) {
        this.friendRepository = friendRepository;
        this.userCacheRepository = userCacheRepository;
        this.jwtUtil = jwtUtil;
    }


    public Friend addFriendByUsername(String username, String token) {
        Long userId = jwtUtil.getUserIdFromToken(token);


        UserCache friendUser = userCacheRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (friendUser.getUserId().equals(userId)) {
            throw new RuntimeException("You cannot add yourself as a friend");
        }

        boolean exists = friendRepository.findByUserIdAndFriendId(userId, friendUser.getUserId()).isPresent();
        if (exists) {
            throw new RuntimeException("User is already your friend");
        }

        Friend friend = new Friend();
        friend.setUserId(userId);
        friend.setFriendId(friendUser.getUserId());

        return friendRepository.save(friend);
    }

    public List<FriendDto> getAllFriends(String token) {
        Long userId = jwtUtil.getUserIdFromToken(token);
        List<Friend> friends = friendRepository.findAllByUserId(userId);

        return friends.stream().map(f -> {
            UserCache cache = userCacheRepository.findById(f.getFriendId())
                    .orElseThrow(() -> new RuntimeException("UserCache not found"));
            return new FriendDto(f.getFriendId(), cache.getUsername());
        }).toList();
    }
}