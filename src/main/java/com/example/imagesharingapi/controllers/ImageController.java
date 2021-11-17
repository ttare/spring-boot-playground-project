package com.example.imagesharingapi.controllers;

import com.example.imagesharingapi.models.dto.ImageDetails;
import com.example.imagesharingapi.models.dto.UserDetails;
import com.example.imagesharingapi.services.ImageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{id}/toggle-like")
    public ImageDetails toggleLike(@PathVariable Long id) {
        return imageService.toggleLike(id);
    }

    @GetMapping("/{id}/toggle-dislike")
    public ImageDetails toggleDisLike(@PathVariable Long id) {
        return imageService.toggleDisLike(id);
    }

    @GetMapping("/{id}/likes/{page}/{size}")
    public List<UserDetails> likes(@PathVariable("id") Long id, @PathVariable("page") int page, @PathVariable("size") int size) {
        return imageService.likes(id, page, size);
    }

    @GetMapping("/{id}/dislikes")
    public List<UserDetails> dislikes(@PathVariable("id") Long id, @PathVariable("page") int page, @PathVariable("size") int size) {
        return imageService.dislikes(id, page, size);
    }
}
