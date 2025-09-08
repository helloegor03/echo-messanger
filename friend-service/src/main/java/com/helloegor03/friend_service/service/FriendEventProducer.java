package com.helloegor03.friend_service.service;

import com.helloegor03.friend_service.dto.FriendEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FriendEventProducer {
    private final KafkaTemplate<String, FriendEvent> kafkaTemplate;

    public FriendEventProducer(KafkaTemplate<String, FriendEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserFriend(FriendEvent event) {
        System.out.println("TRY SEND EVENT: " + event);
        kafkaTemplate.send("add-friend-topic", event).whenComplete((result, ex) -> {
            if (ex != null) {
                System.err.println("Failed to send: " + ex.getMessage());
            } else {
                System.out.println("Sent! offset=" + result.getRecordMetadata().offset());
            }
        });
    }
}
