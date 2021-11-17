package com.example.imagesharingapi.services;

import com.example.imagesharingapi.models.dao.Tag;
import com.example.imagesharingapi.models.dto.Suggestion;
import com.example.imagesharingapi.repositories.TagRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final UserService userService;


    public TagService(TagRepository tagRepository, UserService userService) {
        this.tagRepository = tagRepository;
        this.userService = userService;
    }

    public Set<Tag> getTags(String[] tagNames) {
        HashSet<String> tagsMap = new HashSet<>();
        for (String tagName : tagNames) {
            tagsMap.add(tagName.toLowerCase());
        }

        Set<Tag> tags = tagRepository.findAllByNameIgnoreCaseIsIn(tagNames);

        for (Tag tag : tags) {
            tagsMap.remove(tag.getName().toLowerCase());
        }

        for (String tagName : tagNames) {
            if (tagsMap.contains(tagName.toLowerCase())) {
                tags.add(new Tag(tagName));
            }
        }

        return tags;
    }

    public Set<Suggestion> getSuggestions(String query) {
        Set<Suggestion> suggestions = new HashSet<>();
        if (query.startsWith("@")) {
            return userService.findAllByQuery(query.substring(1));
        }

        if (query.startsWith("#")) {
            return findAllByQuery(query.substring(1));
        }

        suggestions.addAll(userService.findAllByQuery(query));
        suggestions.addAll(findAllByQuery(query));

        return suggestions;
    }

    private Set<Suggestion> findAllByQuery(String query) {
        return tagRepository.findAllByNameIsContainingIgnoreCase(query)
                .stream()
                .map(Suggestion::new)
                .collect(Collectors.toSet());
    }
}
