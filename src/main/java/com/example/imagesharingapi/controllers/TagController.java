package com.example.imagesharingapi.controllers;

import com.example.imagesharingapi.models.dto.Suggestion;
import com.example.imagesharingapi.services.TagService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/suggestions")
    public Set<Suggestion> getSuggestions(@Param("query") String query) {
        return tagService.getSuggestions(query);
    }
}
