package com.pgbd.dadinhoapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record LevelRegisterDTO(
        @NotBlank
        String icon,
        @NotBlank
        String title,
        @NotEmpty
        List<String> answers
) {
}
