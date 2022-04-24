package com.example.demo.security.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder

public class SignupRequest {
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    @NotEmpty(message = "Username is required")
    private String username;
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
    @Email(message = "Please provide a valid Email", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    private Set<String> roles;
}
