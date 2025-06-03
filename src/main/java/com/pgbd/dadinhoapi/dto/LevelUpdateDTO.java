package com.pgbd.dadinhoapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record LevelUpdateDTO(
        @NotNull
        Long id,
        @NotBlank
        String icon,
        @NotBlank
        String title,
        @NotEmpty
        List<String> answers
) {
}
