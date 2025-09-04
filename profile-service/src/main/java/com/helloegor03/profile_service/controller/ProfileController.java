package com.helloegor03.profile_service.controller;

import com.helloegor03.profile_service.model.Profile;
import com.helloegor03.profile_service.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/profile")
@RestController
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
    @PostMapping("/add")
    public ResponseEntity<?> addProfile(Authentication authentication,@RequestBody Profile profile) {
        profile.setUsername(authentication.getName());
        Profile savedProfile = profileService.createProfile(profile);
        return ResponseEntity.ok(savedProfile);
    }

}
