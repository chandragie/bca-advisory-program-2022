package com.bca.adam.service;

import java.util.Optional;

import com.bca.adam.model.Login;
import com.bca.adam.repository.LoginRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;

@Service
public class LoginService {

    @Autowired
    LoginRepository loginRepo;

    public String extractUserIdFromValidJWT(Claims claims) {
        Optional<Login> _login = loginRepo.findBySessionidAndIsValidTrue(claims.get("sessionid").toString());
        if (_login.isPresent())
            return _login.get().getUserId();
        else
            return null;
    }
}
