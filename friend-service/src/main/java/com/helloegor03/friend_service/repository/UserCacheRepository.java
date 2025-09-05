package com.helloegor03.friend_service.repository;

import com.helloegor03.friend_service.model.UserCache;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCacheRepository extends JpaRepository<UserCache, Long> {
    Optional<UserCache> findByUsername(String username);
}
