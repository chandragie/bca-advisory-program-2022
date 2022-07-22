package com.bca.adam.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/common")
public class CommonController {

    @GetMapping("/unauthorized")
    public ResponseEntity<HttpStatus> unauthorized() {
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
