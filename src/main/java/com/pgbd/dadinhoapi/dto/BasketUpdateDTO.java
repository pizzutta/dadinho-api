package com.pgbd.dadinhoapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BasketUpdateDTO(
        @NotNull
        Long id,
        @NotBlank
        String name,
        @NotEmpty
        List<Long> itemsIds
) {
}
