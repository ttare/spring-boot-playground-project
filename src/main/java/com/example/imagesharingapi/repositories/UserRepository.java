package com.example.imagesharingapi.repositories;

import com.example.imagesharingapi.models.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmailIgnoreCaseOrUsernameIgnoreCase(String email, String username);
    public Set<User> findAllByIdIsIn(Long[] ids);
    public Set<User> findAllByNameIgnoreCaseIsContaining(@NotBlank String name);
}