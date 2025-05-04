package com.pgbd.dadinhoapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BasketUpdateDTO(
        @NotNull
        Long id,
        @NotEmpty
        List<Long> itemsIds
) {
}
