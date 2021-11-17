package com.example.imagesharingapi.repositories;

import com.example.imagesharingapi.models.dao.Vote;
import com.example.imagesharingapi.utils.VoteEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends PagingAndSortingRepository<Vote, Long> {
    public List<Vote> findAllByImageIdIsIn(Long[] imagesIds);
    public List<Vote> findAllByImageIdAndVote(Long imageId, VoteEnum voteEnum, Pageable pageable);
    public Optional<Vote> findByImageIdAndUserId(Long imageId, Long userId);
}
