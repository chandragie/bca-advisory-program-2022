package com.bca.adam.repository;

import java.util.Optional;
import java.util.UUID;

import com.bca.adam.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findById(UUID id);

    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);
}
