package com.example.auth_service.dto;

public class UserCreatedEvent {
    public Long userId;
    public String username;

    public UserCreatedEvent(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }


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
