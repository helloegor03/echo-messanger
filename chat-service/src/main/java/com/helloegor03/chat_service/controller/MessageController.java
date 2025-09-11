package com.helloegor03.chat_service.controller;

import com.helloegor03.chat_service.dto.ChatHistoryResponse;
import com.helloegor03.chat_service.dto.SendMessageRequest;
import com.helloegor03.chat_service.model.Message;
import com.helloegor03.chat_service.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send/{receiverId}")
    public ResponseEntity<Message> sendMessage(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long receiverId,
            @RequestBody SendMessageRequest request
    ) {
        String jwt = authHeader.substring(7);
        return ResponseEntity.ok(messageService.sendMessage(jwt, receiverId, request.text()));
    }


    @GetMapping("/history/{friendId}")
    public ResponseEntity<ChatHistoryResponse> getHistory(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long friendId
    ) {
        String jwt = authHeader.substring(7);
        return ResponseEntity.ok(messageService.getChatHistory(jwt, friendId));
    }
}
