package com.example.imagesharingapi.repositories;

import com.example.imagesharingapi.models.dao.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    public Set<Tag> findAllByNameIgnoreCaseIsIn(String[] tagNames);
    public Set<Tag> findAllByNameIsContainingIgnoreCase(String query);
}
