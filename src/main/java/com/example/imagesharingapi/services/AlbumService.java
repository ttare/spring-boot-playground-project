package com.example.imagesharingapi.services;

import com.example.imagesharingapi.models.MyUserDetails;
import com.example.imagesharingapi.models.dao.Album;
import com.example.imagesharingapi.models.dao.User;
import com.example.imagesharingapi.models.dao.Vote;
import com.example.imagesharingapi.models.dto.*;
import com.example.imagesharingapi.repositories.AlbumRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final ImageService imageService;
    private final VoteService voteService;

    public AlbumService(AlbumRepository albumRepository, AuthenticationManager authenticationManager, ImageService imageService, VoteService voteService) {
        this.albumRepository = albumRepository;
        this.imageService = imageService;
        this.voteService = voteService;
    }

    public Album create(CreateAlbum createAlbum) {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = myUserDetails.getUser();
        Album album = new Album(createAlbum.getName());
        album.setUser(user);
        return albumRepository.save(album);
    }

    public Album update(Long albumId, CreateAlbum updateAlbum) {
        Album album = findAlbum(albumId);
        album.setName(updateAlbum.getName());
        return albumRepository.save(album);
    }

    public void deleteById(Long albumId) {
        albumRepository.deleteById(albumId);
    }

    public AlbumDetails findById(Long albumId) {
        Optional<Album> album = albumRepository.findById(albumId);
        if (!album.isPresent()) {
            throw new IllegalArgumentException("Invalid id");
        }
        return new AlbumDetails(album.get());
    }

    public List<AlbumDto> findAllByUser() {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return albumRepository.findAllByUserIdAndCount(myUserDetails.getUser().getId());
    }

    public ImageDetails addImage(Long albumId, CreateImage createImage) {
        Album album = findAlbum(albumId);
        return imageService.save(album, createImage);
    }

    private Album findAlbum(Long albumId) {
        Optional<Album> album = albumRepository.findById(albumId);

        if (!album.isPresent()) {
            throw new IllegalArgumentException("invalid id");
        }

        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = myUserDetails.getUser();

        if (!album.get().getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("invalid id");
        }

        return album.get();
    }
}
