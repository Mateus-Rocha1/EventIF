package com.eventif.dto;

import com.eventif.model.Perfil;
import com.eventif.model.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public record UsuarioResponseDto(
        Long id,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm,
        String email,
        String nome,
        boolean ativo,
        List<String> perfis
) {
    public static UsuarioResponseDto toDto(Usuario usuario) {
        return new UsuarioResponseDto(
                usuario.getId(),
                usuario.getCriadoEm(),
                usuario.getAtualizadoEm(),
                usuario.getEmail(),
                usuario.getNome(),
                usuario.isAtivo(),
                usuario.getPerfis().stream().map(Perfil::getNome).toList()
        );
    }
}
