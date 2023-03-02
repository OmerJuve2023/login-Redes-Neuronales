package com.neuronales.login.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class NewUser {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;
    private Set<String> roles = new HashSet<>();

    public NewUser() {
    }

    public NewUser(@NotBlank String userName, @NotBlank String password,
                   Set<String> roles) {

        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }


}
