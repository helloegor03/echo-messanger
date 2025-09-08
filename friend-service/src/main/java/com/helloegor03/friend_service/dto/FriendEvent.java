package com.helloegor03.friend_service.dto;

public class FriendEvent {
    private Long userId;
    private Long friendId;

    public FriendEvent(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public FriendEvent() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }
}
