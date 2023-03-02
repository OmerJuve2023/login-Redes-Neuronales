package com.neuronales.login.repositories;

import com.neuronales.login.models.Authority;
import com.neuronales.login.utls.AuthorityName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByName(AuthorityName name);
}
