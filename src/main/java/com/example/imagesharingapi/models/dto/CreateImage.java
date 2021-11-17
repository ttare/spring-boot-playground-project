package com.example.imagesharingapi.models.dto;

public class CreateImage {
    private String name;
    private String[] tags;
    private Long[] watchers;

    public CreateImage() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Long[] getWatchers() {
        return watchers;
    }

    public void setWatchers(Long[] watchers) {
        this.watchers = watchers;
    }
}
