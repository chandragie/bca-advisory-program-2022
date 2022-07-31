package com.bca.adam.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/common")
public class CommonController {

    @GetMapping("/unauthorized")
    public ResponseEntity<String> unauthorized() {
        return new ResponseEntity<>("You are not allowed to access this page! Get the hell outta here",
                HttpStatus.UNAUTHORIZED);
    }
}
