package com.helloegor03.friend_service.repository;

import com.helloegor03.friend_service.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> findByUserIdAndFriendId(Long userId, Long friendId);
    List<Friend> findAllByUserId(Long userId);
}
