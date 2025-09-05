package com.helloegor03.friend_service.dto;

public class FriendDto {
    private Long friendId;
    private String username;

    public FriendDto(Long friendId, String username) {
        this.friendId = friendId;
        this.username = username;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
