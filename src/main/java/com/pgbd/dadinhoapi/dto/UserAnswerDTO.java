package com.pgbd.dadinhoapi.dto;

import com.pgbd.dadinhoapi.annotation.ValidUserRole;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import static com.pgbd.dadinhoapi.model.UserRole.STUDENT;

public record UserAnswerDTO(
        @NotNull
        @ValidUserRole(role = STUDENT)
        Long userId,
        @NotNull
        Long levelId,
        @NotNull
        Integer totalTime,
        @NotEmpty
        List<String> userAnswers
) {
}
