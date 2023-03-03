package com.neuronales.login.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String email;
    private String password;
    private String firstname;
    private String lastname;


}
