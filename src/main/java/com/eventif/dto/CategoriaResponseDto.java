package com.eventif.dto;

import com.eventif.model.Categoria;

public record CategoriaResponseDto(
        Long id,
        String nome
) {
    public static CategoriaResponseDto toDto(Categoria categoria) {
        return new CategoriaResponseDto(
                categoria.getId(),
                categoria.getNome()
        );
    }
}
