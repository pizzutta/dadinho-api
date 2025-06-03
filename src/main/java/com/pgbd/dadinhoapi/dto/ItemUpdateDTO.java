package com.pgbd.dadinhoapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemUpdateDTO(
        @NotNull
        Long id,
        @NotBlank
        String icon,
        @NotBlank
        String name
) {
}
