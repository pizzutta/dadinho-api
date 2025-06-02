package com.pgbd.dadinhoapi.dto;

import jakarta.validation.constraints.*;

import java.util.List;

public record ClassRegisterDTO(
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
