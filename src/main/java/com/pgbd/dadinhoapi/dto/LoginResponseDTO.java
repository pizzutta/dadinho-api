package com.pgbd.dadinhoapi.dto;

public record LoginResponseDTO(
        Long id,
        String email,
        String role,
        String token
) {
}
