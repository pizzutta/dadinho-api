package com.pgbd.dadinhoapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemRecipeRegisterDTO(
        @NotNull
        Long itemId,
        @NotNull
        @Positive
        Integer quantity,
        @NotNull
        Long levelId
) {
}
