package com.neuronales.login.services;

import com.neuronales.login.models.User;
import com.neuronales.login.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> getByUserName(String userName) {
        return repository.findByUsername(userName);
    }

    public boolean existByUserName(String userName) {
        return repository.existsUserByUsername(userName);
    }

    public void save(User user) {
        repository.save(user);
    }
}
