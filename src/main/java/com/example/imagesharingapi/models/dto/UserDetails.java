package com.example.imagesharingapi.models.dto;

import com.example.imagesharingapi.models.dao.User;

public class UserDetails {
    private Long id;
    private String name;
    private String email;
    private String username;

    public UserDetails() {
    }

    public UserDetails(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
