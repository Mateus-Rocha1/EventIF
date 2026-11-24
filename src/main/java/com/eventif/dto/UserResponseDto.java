package com.eventif.dto;

import com.eventif.model.Roles;
import com.eventif.model.User;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponseDto(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String email,
        String nome,
        List<Roles> roles
) {
    public static UserResponseDto toDto(User usuario) {
        return new UserResponseDto(
                usuario.getId(),
                usuario.getCreatedAt(),
                usuario.getUpdatedAt(),
                usuario.getEmail(),
                usuario.getNome(),
                usuario.getRoles()
        );
    }
}
