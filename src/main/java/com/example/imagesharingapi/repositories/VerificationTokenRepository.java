package com.example.imagesharingapi.repositories;

import com.example.imagesharingapi.models.dao.VerificationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {
    public Optional<VerificationToken> findByToken(String token);
}
