package com.pgbd.dadinhoapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemRecipeUpdateDTO(
        @NotNull
        Long id,
        @NotNull
        Long itemId,
        @NotNull
        @Positive
        Integer quantity
) {
}
