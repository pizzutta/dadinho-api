package com.pgbd.dadinhoapi.dto;

import jakarta.validation.constraints.NotBlank;

public record ItemRegisterDTO(
        @NotBlank
        String icon,
        @NotBlank
        String name
) {
}
