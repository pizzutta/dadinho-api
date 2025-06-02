package com.pgbd.dadinhoapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record VerifyUserAnswerDTO(
        @NotNull
        Long userId,
        @NotNull
        Long levelId,
        @NotNull
        Integer totalTime,
        @NotEmpty
        List<String> userAnswers
) {
}
