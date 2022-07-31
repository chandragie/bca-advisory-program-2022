package com.bca.adam.controller;

import com.bca.adam.model.User;
import com.bca.adam.repository.UserRepository;
import com.bca.adam.util.MD5Crypto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepo;

    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            if (null != user.getName() && null != user.getUsername() && null != user.getPassword()) {

                // check if user already exists
                if (userRepo.findByUsername(user.getUsername()).isPresent()) {
                    return new ResponseEntity<>(null, HttpStatus.CONFLICT);
                }

                User _user = userRepo
                        .save(new User(user.getUsername(), user.getName(), MD5Crypto.hash(user.getPassword())));

                return new ResponseEntity<>(_user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
