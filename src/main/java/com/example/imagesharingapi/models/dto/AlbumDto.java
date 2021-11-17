package com.example.imagesharingapi.models.dto;

import java.util.Date;


public interface AlbumDto {
    Long getId();
    String getName();
    Date getCreatedAt();
    int getImages();
}
