package com.helloegor03.chat_service.repository;

import com.helloegor03.chat_service.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m " +
            "WHERE (m.senderId = :userId AND m.receiverId = :friendId) " +
            "   OR (m.senderId = :friendId AND m.receiverId = :userId) " +
            "ORDER BY m.timestamp ASC")
    List<Message> findChatHistory(@Param("userId") Long userId, @Param("friendId") Long friendId);
}
