package com.pgbd.dadinhoapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record ClassUpdateDTO(
        @NotNull
        Long id,
        @NotBlank
        String name,
        @Positive
        Integer grade,
        @NotNull
        Long teacherId,
        @NotEmpty
        List<Long> studentsIds
) {
}
