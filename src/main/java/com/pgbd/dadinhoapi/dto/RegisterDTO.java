package com.pgbd.dadinhoapi.dto;

import com.pgbd.dadinhoapi.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotNull
        UserRole role
) {
}
