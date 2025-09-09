package com.helloegor03.chat_service.service;

import com.helloegor03.chat_service.config.JwtUtil;
import com.helloegor03.chat_service.model.Message;
import com.helloegor03.chat_service.repository.MessageRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final JwtUtil jwtUtil;

    public MessageService(MessageRepository messageRepository, JwtUtil jwtUtil) {
        this.messageRepository = messageRepository;
        this.jwtUtil = jwtUtil;
    }

    public Message sendMessage(String jwt, Long receiverId, String text) {
        Long senderId = jwtUtil.getUserIdFromToken(jwt);

        Message msg = new Message();
        msg.setSenderId(senderId);
        msg.setReceiverId(receiverId);
        msg.setText(text);

        return messageRepository.save(msg);
    }

    public List<Message> getChatHistory(String jwt, Long friendId) {
        Long userId = jwtUtil.getUserIdFromToken(jwt);
        return messageRepository.findChatHistory(userId, friendId);
    }
}
