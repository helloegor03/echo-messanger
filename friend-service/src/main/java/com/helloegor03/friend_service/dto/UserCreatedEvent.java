package com.helloegor03.friend_service.dto;


public class UserCreatedEvent {
    private Long userId;
    private String username;


    public UserCreatedEvent(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public UserCreatedEvent() {}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}