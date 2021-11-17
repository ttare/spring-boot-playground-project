package com.example.imagesharingapi.services;

import com.example.imagesharingapi.models.dao.VerificationToken;
import com.example.imagesharingapi.repositories.VerificationTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;

    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public Optional<VerificationToken> findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

}
