package com.helloegor03.friend_service.controller;

import com.helloegor03.friend_service.dto.AddFriendRequest;
import com.helloegor03.friend_service.dto.FriendDto;
import com.helloegor03.friend_service.dto.FriendEvent;
import com.helloegor03.friend_service.service.FriendEventProducer;
import com.helloegor03.friend_service.service.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {

    private final FriendService friendService;
    private final FriendEventProducer friendEventProducer;

    public FriendController(FriendService friendService, FriendEventProducer friendEventProducer) {
        this.friendService = friendService;
        this.friendEventProducer = friendEventProducer;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFriend(
            @RequestBody AddFriendRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        String jwt = authHeader.substring(7);
        var friend = friendService.addFriendByUsername(request.getUsername(), jwt);


        friendEventProducer.sendUserFriend(
                new FriendEvent(friend.getUserId(), friend.getFriendId())
        );

        return ResponseEntity.ok(friend);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<FriendDto>> getAllFriends(
            @RequestHeader("Authorization") String authHeader
    ) {
        String jwt = authHeader.substring(7);
        List<FriendDto> friends = friendService.getAllFriends(jwt);
        return ResponseEntity.ok(friends);
    }
}
