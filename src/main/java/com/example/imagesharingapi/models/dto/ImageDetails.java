package com.example.imagesharingapi.models.dto;

import com.example.imagesharingapi.models.dao.Image;
import com.example.imagesharingapi.models.dao.Tag;
import com.example.imagesharingapi.models.dao.User;
import com.example.imagesharingapi.utils.VoteEnum;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ImageDetails {
    private Long id;
    private String name;
    private Date createdAt;
    private Set<Tag> tags = new HashSet<>();
    private Set<UserDetails>watchers = new HashSet<>();
    private UserDetails publisher;
    private int likes = 0;
    private int dislikes = 0;
    private boolean likedByYou;
    private boolean dislikedByYou;

    public ImageDetails() {
    }

    public ImageDetails(Image image, User user) {
        this.id = image.getId();
        this.name = image.getName();
        this.createdAt = image.getCreatedAt();

        this.tags = image.getTags().stream().map(tag -> {
            tag.setCreatedAt(null);
            return tag;
        }).collect(Collectors.toSet());

        this.publisher = new UserDetails(image.getAlbum().getUser());

        this.watchers = image.getWatchers().stream().map(UserDetails::new).collect(Collectors.toSet());

        Long userId = user != null ? user.getId() : null;

        image.getVotes().stream().forEach((vote -> {
            if (user != null && userId.equals(vote.getUser().getId())) {
                if (vote.getVote().equals(VoteEnum.LIKE)) {
                    this.likedByYou = true;
                    this.dislikedByYou = false;
                } else {
                    this.likedByYou = false;
                    this.dislikedByYou = true;
                }
            }
            if (vote.getVote().equals(VoteEnum.LIKE)) {
                this.likes++;
            } else {
                this.dislikes++;
            }
        }));
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<UserDetails> getWatchers() {
        return watchers;
    }

    public void setWatchers(Set<UserDetails> watchers) {
        this.watchers = watchers;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public boolean isLikedByYou() {
        return likedByYou;
    }

    public void setLikedByYou(boolean likedByYou) {
        this.likedByYou = likedByYou;
    }

    public boolean isDislikedByYou() {
        return dislikedByYou;
    }

    public void setDislikedByYou(boolean dislikedByYou) {
        this.dislikedByYou = dislikedByYou;
    }

    public UserDetails getPublisher() {
        return publisher;
    }

    public void setPublisher(UserDetails publisher) {
        this.publisher = publisher;
    }
}
