package com.example.imagesharingapi.controllers;

import com.example.imagesharingapi.models.dao.User;
import com.example.imagesharingapi.models.dto.CreateUser;
import com.example.imagesharingapi.services.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User create(@RequestBody CreateUser createUser) {
        return userService.create(createUser);
    }

    @GetMapping("/verify")
    public boolean verify(@Param("token") String token) {
        return userService.verify(token);
    }

    @GetMapping("/me")
    public User me() {
        return userService.me();
    }

    @PostMapping("/{id}/update")
    public User update(@RequestBody CreateUser updateUser, @PathVariable Long id) {
        return userService.update(id, updateUser);
    }

    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }

    @GetMapping("/{id}")
    public Optional<User> list(@PathVariable Long id) {
        return userService.details(id);
    }
}
