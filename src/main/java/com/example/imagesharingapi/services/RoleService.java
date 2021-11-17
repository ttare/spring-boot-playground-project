package com.example.imagesharingapi.services;

import com.example.imagesharingapi.models.dao.Role;
import com.example.imagesharingapi.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role>findByName(String name) {
        return roleRepository.findByName(name);
    }
}
