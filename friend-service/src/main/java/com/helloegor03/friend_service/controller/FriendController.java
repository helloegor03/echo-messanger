package com.helloegor03.friend_service.controller;

import com.helloegor03.friend_service.model.Friend;
import com.helloegor03.friend_service.service.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @PostMapping("/add")
    public ResponseEntity<Friend> addFriend(
            @RequestBody Friend friend,
            @RequestHeader("Authorization") String authHeader
    ) {
        String jwt = authHeader.substring(7);
        Friend savedFriend = friendService.addFriend(friend, jwt);
        return ResponseEntity.ok(savedFriend);
    }

    @DeleteMapping("/delete/{friendId}")
    public ResponseEntity<String> deleteFriend(
            @PathVariable Long friendId,
            @RequestHeader("Authorization") String authHeader
    ) {
        String jwt = authHeader.substring(7);
        friendService.deleteFriend(friendId, jwt);
        return ResponseEntity.ok("Friend deleted");
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Friend>> getAllFriends(
            @RequestHeader("Authorization") String authHeader
    ) {
        String jwt = authHeader.substring(7);
        List<Friend> friends = friendService.getAllFriends(jwt);
        return ResponseEntity.ok(friends);
    }
}