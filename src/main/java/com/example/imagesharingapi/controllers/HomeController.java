package com.example.imagesharingapi.controllers;

import com.example.imagesharingapi.models.dto.ImageDetails;
import com.example.imagesharingapi.services.ImageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/home")
public class HomeController {
    private final ImageService imageService;

    public HomeController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/images")
    public List<ImageDetails> list () {
        return imageService.list();
    }
}
