package com.example.imagesharingapi.services;

import com.example.imagesharingapi.models.dao.Vote;
import com.example.imagesharingapi.repositories.VoteRepository;
import com.example.imagesharingapi.utils.VoteEnum;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VoteService {
    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public List<Vote> findAllByImageIdIsIn(Long[] imagesIds) {
        return voteRepository.findAllByImageIdIsIn(imagesIds);
    }

    public List<Vote> findAllByImageId(Long imageId, VoteEnum voteEnum, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return (List<Vote>) voteRepository.findAllByImageIdAndVote(imageId, voteEnum, pageable);
    }

    private void toggleLikeOrDislike(Long imageId, Long userId, VoteEnum voteEnum) {
        Vote vote = voteRepository.findByImageIdAndUserId(imageId, userId).orElse(new Vote(imageId, userId));

        if (vote.getId() != null) {
            if (vote.getVote().equals(voteEnum)) {
                voteRepository.deleteById(vote.getId());
                return;
            }
        }

        vote.setVote(voteEnum);
        voteRepository.save(vote);
    }

    public void toggleLike(Long imageId, Long userId) {
       toggleLikeOrDislike(imageId, userId, VoteEnum.LIKE);
    }

    public void toggleDislike(Long imageId, Long userId) {
        toggleLikeOrDislike(imageId, userId, VoteEnum.DISLIKE);
    }
}
