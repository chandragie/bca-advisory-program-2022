package com.bca.adam.service;

import java.util.Optional;
import java.util.UUID;

import com.bca.adam.model.User;
import com.bca.adam.repository.UserRepository;
import com.bca.adam.util.MD5Crypto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    public String getUserIdByUsernameAndPassword(String username, String password) {
        String userId = null;
        Optional<User> _user = userRepo.findByUsernameAndPassword(username, MD5Crypto.hash(password));
        if (_user.isPresent()) {
            userId = _user.get().getId().toString();
        }
        return userId;
    }

    public User getUserById(String id) {
        Optional<User> _user = userRepo.findById(UUID.fromString(id));
        if (_user.isPresent())
            return _user.get();
        else
            return null;
    }

}
