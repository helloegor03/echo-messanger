package com.helloegor03.profile_service.service;

import com.helloegor03.profile_service.model.Profile;
import com.helloegor03.profile_service.repository.ProfileRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Optional<Profile> getCurrentUsername(Authentication authentication){
        String username = authentication.getName();
        return profileRepository.findByUsername(username);
    }

    public Profile createProfile(Profile profile){
        Optional<Profile> existingProfileOpt = profileRepository.findByUsername(profile.getUsername());
        Profile profileToSave = existingProfileOpt.orElse(new Profile());

        profileToSave.setName(profile.getName());
        profileToSave.setUsername(profile.getUsername());
        profileToSave.setBirthday(profile.getBirthday());
        profileToSave.setDescription(profile.getDescription());

        return profileRepository.save(profileToSave);
    }
}
