package com.example.imagesharingapi.models.dto;

import com.example.imagesharingapi.models.dao.Tag;
import com.example.imagesharingapi.models.dao.User;

public class Suggestion {
    private Long id;
    private String name;
    private Boolean isUser;

    public Suggestion() {
    }

    public Suggestion(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }


    public Suggestion(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.isUser = true;
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

    public Boolean getUser() {
        return isUser;
    }

    public void setUser(Boolean user) {
        isUser = user;
    }
}
