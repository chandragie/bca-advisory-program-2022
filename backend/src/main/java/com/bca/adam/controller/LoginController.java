package com.bca.adam.controller;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.bca.adam.model.Login;
import com.bca.adam.model.User;
import com.bca.adam.repository.LoginRepository;
import com.bca.adam.service.LoginService;
import com.bca.adam.service.UserService;
import com.bca.adam.util.JWTTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/sign")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

    @Autowired
    LoginRepository loginRepo;

    @PostMapping("/in")
    @Transactional
    public ResponseEntity<String> signin(@RequestBody User user) {
        String jwt = null;
        try {
            if (null != user.getUsername() && null != user.getPassword()) {
                String userId = userService.getUserIdByUsernameAndPassword(user.getUsername(), user.getPassword());
                if (null != userId) {
                    // user and password found, allow login
                    Login _login = loginRepo.save(new Login(userId)); // insert login data

                    // generate jwt
                    String current = new Date().getTime() + "";
                    HashMap<String, String> claims = new HashMap<>();
                    claims.put("sessionid", _login.getSessionid());
                    claims.put("createddate", _login.getCreatedDate().toString());
                    claims.put("isvalid", current.substring(0, 6) + _login.isValid() + current.substring(6));
                    jwt = JWTTokenizer.generateJWT(claims);

                    return new ResponseEntity<>(jwt, HttpStatus.OK);

                } else {
                    return new ResponseEntity<>("User not found", HttpStatus.OK); // user not found
                }

            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // @PostMapping("/logout")
    @PostMapping("/out")
    @Transactional
    public ResponseEntity<String> signout(HttpServletRequest req) {

        try {
            // validate and extract jwt claims
            Claims claim = JWTTokenizer.validateJWT(req.getHeader("Authorization").toString());
            if (null == claim)
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

            String userId = loginService.extractUserIdFromValidJWT(claim);
            if (userId == null)
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

            User _user = userService.getUserById(userId);
            if (_user == null)
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

            loginRepo.logout(_user.getId().toString());

            return new ResponseEntity<>(_user.getUsername() + " has successfully logged out", HttpStatus.OK);

        } catch (MalformedJwtException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
