package com.pgbd.dadinhoapi.dto;

import jakarta.validation.constraints.NotNull;

public record IdDTO(
        @NotNull
        Long id
) {
}
