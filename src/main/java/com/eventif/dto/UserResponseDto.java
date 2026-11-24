package com.eventif.dto;

import com.eventif.model.Perfil;
import com.eventif.model.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponseDto(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String email,
        String nome,
        boolean ativo,
        List<String> perfis
) {
    public static UserResponseDto toDto(Usuario usuario) {
        return new UserResponseDto(
                usuario.getId(),
                usuario.getCreatedAt(),
                usuario.getUpdatedAt(),
                usuario.getEmail(),
                usuario.getNome(),
                usuario.isAtivo(),
                usuario.getPerfis().stream().map(Perfil::getNome).toList()
        );
    }
}
