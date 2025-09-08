package com.helloegor03.chat_service.service;

import com.helloegor03.chat_service.config.JwtUtil;
import com.helloegor03.chat_service.model.Message;
import com.helloegor03.chat_service.repository.MessageRepository;
import com.helloegor03.chat_service.repository.UserFriendsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final JwtUtil jwtUtil;
    private final MessageRepository messageRepository;
    private final UserFriendsRepository userFriendsRepository;

    public MessageService(JwtUtil jwtUtil, MessageRepository messageRepository, UserFriendsRepository userFriendsRepository) {
        this.jwtUtil = jwtUtil;
        this.messageRepository = messageRepository;
        this.userFriendsRepository = userFriendsRepository;
    }

    public Message sendMessage(String token, Long receiverId, String text) {
        Long senderId = jwtUtil.getUserIdFromToken(token);

        boolean isFriend = userFriendsRepository.existsByUserIdAndFriendId(senderId, receiverId)
                || userFriendsRepository.existsByUserIdAndFriendId(receiverId, senderId);

        if (!isFriend) {
            throw new IllegalArgumentException("That's not your friend");
        }

        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setText(text);

        return messageRepository.save(message);
    }

    public List<Message> getChatHistory(String token, Long friendId) {
        Long userId = jwtUtil.getUserIdFromToken(token);

        return messageRepository.findBySenderIdAndReceiverId(userId, friendId);
    }
}
