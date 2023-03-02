package com.neuronales.login.services;

import com.neuronales.login.models.Rol;
import com.neuronales.login.models.RolList;
import com.neuronales.login.repositories.RolRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class RolService {
    private final RolRepository repository;

    public Optional<Rol> getByRolName(RolList roleName) {
        return repository.findByName(roleName);
    }
}
