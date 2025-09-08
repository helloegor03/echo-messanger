package com.helloegor03.chat_service.repository;

import com.helloegor03.chat_service.model.UserFriends;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFriendsRepository extends JpaRepository<UserFriends, Long> {
    boolean existsByUserIdAndFriendId(Long userId, Long friendId);
}
