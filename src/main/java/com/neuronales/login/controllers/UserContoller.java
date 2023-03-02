package com.neuronales.login.controllers;

import com.neuronales.login.services.SecurityUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserContoller {
    private final SecurityUserDetailsService service;

    public UserContoller(SecurityUserDetailsService service) {
        this.service = service;
    }

    @GetMapping("/enter")
    public ResponseEntity<String> hola() {
        return ResponseEntity.ok("se entro correctamente");
    }

}
