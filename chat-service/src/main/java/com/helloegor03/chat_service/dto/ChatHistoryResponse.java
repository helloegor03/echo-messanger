package com.helloegor03.chat_service.dto;

import com.helloegor03.chat_service.model.Message;

import java.util.List;

public record ChatHistoryResponse(Long currentUserId, List<Message> messages) {}
