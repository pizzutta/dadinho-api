package com.pgbd.dadinhoapi.dto;

import jakarta.validation.constraints.*;

import java.util.List;

public record ClassRegisterDTO(
        @NotBlank
        String name,
        @Min(6) @Max(9)
        Integer grade,
        @NotNull
        Long teacherId,
        @NotEmpty
        List<Long> studentsIds
) {
}
