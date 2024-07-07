package com.pgbd.dadinhoapi.dto;

import com.pgbd.dadinhoapi.model.User;

public record UserResponseDTO(
        User user,
        String className,
        Integer classGrade,
        User classTeacher
) {
}
