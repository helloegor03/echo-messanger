package com.helloegor03.friend_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helloegor03.friend_service.dto.UserCreatedEvent;
import com.helloegor03.friend_service.model.UserCache;
import com.helloegor03.friend_service.repository.UserCacheRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserEventConsumer {

    private final UserCacheRepository userCacheRepository;

    public UserEventConsumer(UserCacheRepository userCacheRepository) {
        this.userCacheRepository = userCacheRepository;
    }

    @KafkaListener(
            topics = "user-created-topic",
            groupId = "friend-service",
            containerFactory = "userCreatedKafkaListenerContainerFactory"
    )
    public void consume(Map<String, Object> event) {
        Long userId = ((Number) event.get("userId")).longValue();
        String username = (String) event.get("username");

        UserCache userCache = new UserCache();
        userCache.setUserId(userId);
        userCache.setUsername(username);

        userCacheRepository.save(userCache);
        System.out.println("Event saved: " + username);
    }

}
