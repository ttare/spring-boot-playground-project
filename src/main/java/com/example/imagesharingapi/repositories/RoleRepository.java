package com.example.imagesharingapi.repositories;

import com.example.imagesharingapi.models.dao.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    public Optional<Role>findByName(String name);
}
