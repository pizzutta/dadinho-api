package com.pgbd.dadinhoapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserUpdateDTO(
        @NotNull
        Long id,
        @NotBlank
        String name,
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
