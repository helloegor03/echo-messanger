package com.example.auth_service.service;

import com.example.auth_service.dto.UserCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventProducer {
    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;

    public UserEventProducer(KafkaTemplate<String, UserCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserCreated(UserCreatedEvent event) {
        System.out.println("🚀 TRY SEND EVENT: " + event);
        kafkaTemplate.send("user-created-topic", event).whenComplete((result, ex) -> {
            if (ex != null) {
                System.err.println("❌ Failed to send: " + ex.getMessage());
            } else {
                System.out.println("✅ Sent! offset=" + result.getRecordMetadata().offset());
            }
        });
    }
}
