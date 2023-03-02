package com.neuronales.login.services;


import com.neuronales.login.repositories.UserRepository;
import com.neuronales.login.security.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optUser = this.repository.findByUsername(username);
        if (optUser.isPresent()) {
            return new SecurityUser(optUser.get());
        }
        throw new UsernameNotFoundException(("usuario no existe" + username));
    }

    public SecurityUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

}
