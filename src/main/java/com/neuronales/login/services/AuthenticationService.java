package com.neuronales.login.services;

import com.neuronales.login.dto.AuthenticationResponse;
import com.neuronales.login.dto.AuthenticationRequest;
import com.neuronales.login.dto.RegisterRequest;
import com.neuronales.login.jwt.JwtService;
import com.neuronales.login.models.Role;
import com.neuronales.login.models.User;
import com.neuronales.login.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .lastname(request.getLastname())
                .firstname(request.getFirstname())
                .rol(Role.USER)
                .build();
        repository.save(user);
        var jwToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwToken)
                .build();
    }
}
