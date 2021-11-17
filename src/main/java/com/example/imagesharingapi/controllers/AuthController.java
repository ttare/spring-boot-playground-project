package com.example.imagesharingapi.controllers;

import com.example.imagesharingapi.models.dao.User;
import com.example.imagesharingapi.models.dto.Credentials;
import com.example.imagesharingapi.config.JwtTokenUtil;
import com.example.imagesharingapi.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Credentials credentials) {
        try {
            User user = userService.login(credentials);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateToken(user))
                    .body(user);
        } catch (BadCredentialsException ex) {
            System.out.println("credentials = " + ex);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( null);
        }
    }
}
