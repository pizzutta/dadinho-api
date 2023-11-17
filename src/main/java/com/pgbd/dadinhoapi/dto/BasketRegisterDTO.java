package com.pgbd.dadinhoapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BasketRegisterDTO(
        @NotNull
        Long levelId,
        @NotEmpty
        List<Long> itemsIds
) {
}
