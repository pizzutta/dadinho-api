package com.pgbd.dadinhoapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
