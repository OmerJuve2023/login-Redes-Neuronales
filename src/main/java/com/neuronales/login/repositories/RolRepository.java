package com.neuronales.login.repositories;

import com.neuronales.login.models.Rol;
import com.neuronales.login.models.RolList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByName(RolList rolName);
}
