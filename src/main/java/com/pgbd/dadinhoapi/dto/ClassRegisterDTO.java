package com.pgbd.dadinhoapi.dto;

import com.pgbd.dadinhoapi.annotation.ValidUserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

import static com.pgbd.dadinhoapi.model.UserRole.STUDENT;
import static com.pgbd.dadinhoapi.model.UserRole.TEACHER;

public record ClassRegisterDTO(
        @NotBlank
        String name,
        @Positive
        Integer grade,
        @NotNull
        @ValidUserRole(role = TEACHER)
        Long teacherId,
        @NotEmpty
        @ValidUserRole(role = STUDENT)
        List<Long> studentsIds
) {
}
