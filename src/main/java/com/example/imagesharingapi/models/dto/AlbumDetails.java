package com.example.imagesharingapi.models.dto;

import com.example.imagesharingapi.models.dao.Album;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AlbumDetails {
    private Long id;
    private String name;
    private Date createdAt;
    private List<ImageDetails> images = new ArrayList<>();

    public AlbumDetails() {
    }

    public AlbumDetails(Album album) {
        this.id = album.getId();
        this.name = album.getName();
        this.createdAt = album.getCreatedAt();
        this.images = album.getImages().stream().map(image -> {
            return new ImageDetails(image, album.getUser());
        }).collect(Collectors.toList());
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

    public List<ImageDetails> getImages() {
        return images;
    }

    public void setImages(List<ImageDetails> images) {
        this.images = images;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
