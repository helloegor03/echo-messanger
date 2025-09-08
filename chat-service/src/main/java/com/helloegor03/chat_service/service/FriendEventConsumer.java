package com.helloegor03.chat_service.service;

import com.helloegor03.chat_service.repository.UserFriendsRepository;
import com.helloegor03.chat_service.model.UserFriends;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FriendEventConsumer {
    private final UserFriendsRepository userFriendsRepository;

    public FriendEventConsumer(UserFriendsRepository userFriendsRepository) {
        this.userFriendsRepository = userFriendsRepository;
    }
    @KafkaListener(
            topics = "add-friend-topic",
            groupId = "chat-service",
            containerFactory = "userFriendsKafkaListenerContainerFactory"
    )
    public void consume(Map<String, Object> event) {
        Long userId = ((Number) event.get("userId")).longValue();
        Long friendId = ((Number) event.get("friendId")).longValue();

        UserFriends userFriends = new UserFriends();
        userFriends.setUserId(userId);
        userFriends.setFriendId(friendId);

        userFriendsRepository.save(userFriends);

    }
}
