package com.training.security.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<String> roles;
}
