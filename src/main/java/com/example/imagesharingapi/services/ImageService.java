package com.example.imagesharingapi.services;

import com.example.imagesharingapi.models.MyUserDetails;
import com.example.imagesharingapi.models.dao.*;
import com.example.imagesharingapi.models.dto.CreateImage;
import com.example.imagesharingapi.models.dto.ImageDetails;
import com.example.imagesharingapi.models.dto.UserDetails;
import com.example.imagesharingapi.repositories.ImageRepository;
import com.example.imagesharingapi.utils.VoteEnum;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final TagService tagService;
    private final UserService userService;
    private final VoteService voteService;

    public ImageService(ImageRepository imageRepository, TagService tagService, UserService userService, VoteService voteService) {
        this.imageRepository = imageRepository;
        this.tagService = tagService;
        this.userService = userService;
        this.voteService = voteService;
    }

    public List<ImageDetails> list() {
        List<Image> images = this.imageRepository.findAll();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal.equals("anonymousUser")) {
            return images.stream().map(image -> {
                return new ImageDetails(image, null);
            }).collect(Collectors.toList());
        }

        User user = ((MyUserDetails) principal).getUser();
            return images.stream().map(image -> {
                return new ImageDetails(image, user);
            }).collect(Collectors.toList());

    }

    public ImageDetails save(Album album, CreateImage createImage) {
        Image image = new Image();
        image.setName(createImage.getName());
        image.setAlbum(album);
        Set<Tag> tags = tagService.getTags(createImage.getTags());
        image.setTags(tags);

        Set<User> watchers = userService.findAllByIdIsIn(createImage.getWatchers());
        image.setWatchers(watchers);

        Image created = imageRepository.save(image);

        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = myUserDetails.getUser();

        return new ImageDetails(created, user);
    }

    public ImageDetails toggleLike(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        if (!image.isPresent()) {
            throw new IllegalArgumentException("Wrong Id");
        }

        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = myUserDetails.getUser();

        voteService.toggleLike(image.get().getId(), user.getId());

        return new ImageDetails(image.get(), user);
    }

    public ImageDetails toggleDisLike(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        if (!image.isPresent()) {
            throw new IllegalArgumentException("Wrong Id");
        }

        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = myUserDetails.getUser();

        voteService.toggleDislike(image.get().getId(),user.getId());

        return new ImageDetails(image.get(), user);
    }

    public List<UserDetails> likes(Long id, int page, int limit) {
        List<Vote>votes = voteService.findAllByImageId(id, VoteEnum.LIKE, page, limit);
        return votes.stream().map(vote -> new UserDetails(vote.getUser())).collect(Collectors.toList());
    }

    public List<UserDetails> dislikes(Long id, int page, int limit) {
        List<Vote>votes = voteService.findAllByImageId(id, VoteEnum.DISLIKE, page, limit);
        return votes.stream().map(vote -> new UserDetails(vote.getUser())).collect(Collectors.toList());
    }
}
