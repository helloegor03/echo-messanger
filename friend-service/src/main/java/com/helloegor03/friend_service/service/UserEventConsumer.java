package com.helloegor03.friend_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helloegor03.friend_service.dto.UserCreatedEvent;
import com.helloegor03.friend_service.model.UserCache;
import com.helloegor03.friend_service.repository.UserCacheRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserEventConsumer {

    private final UserCacheRepository userCacheRepository;

    public UserEventConsumer(UserCacheRepository userCacheRepository) {
        this.userCacheRepository = userCacheRepository;
    }

    @KafkaListener(
            topics = "user-created-topic",
            groupId = "friend-service",
            containerFactory = "userCreatedEventKafkaListenerContainerFactory"
    )
    public void consume(UserCreatedEvent event) {
        System.out.println("🚀 Received event: " + event);

        UserCache userCache = new UserCache();
        userCache.setUserId(event.getUserId());
        userCache.setUsername(event.getUsername());

        userCacheRepository.save(userCache);

        System.out.println("✅ Event saved in echouserscache: " + event.getUsername());
    }
}
