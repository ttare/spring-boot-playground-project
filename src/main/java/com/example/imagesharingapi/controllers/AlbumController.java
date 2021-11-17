package com.example.imagesharingapi.controllers;

import com.example.imagesharingapi.models.dao.Album;
import com.example.imagesharingapi.models.dao.Image;
import com.example.imagesharingapi.models.dto.*;
import com.example.imagesharingapi.services.AlbumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @PostMapping("/create")
    public Album create(@RequestBody CreateAlbum createAlbum) {
       return albumService.create(createAlbum);
    }

    @PostMapping("/{id}/update")
    public Album list(@PathVariable Long id, @RequestBody CreateAlbum updateAlbum) {
       return albumService.update(id, updateAlbum);
    }

    @GetMapping("/{id}/remove")
    public ResponseEntity<Boolean> list(@PathVariable Long id) {
       albumService.deleteById(id);
       return ResponseEntity.ok().body(true);
    }

    @GetMapping("/{id}")
    public AlbumDetails details(@PathVariable Long id) {
       return albumService.findById(id);
    }

    @GetMapping("/list")
    public List<AlbumDto> list() {
       return albumService.findAllByUser();
    }

    @PostMapping("/{id}/add-image")
    public ImageDetails addImage(@PathVariable Long id, @RequestBody CreateImage createImage) {
       return albumService.addImage(id, createImage);
    }
}
