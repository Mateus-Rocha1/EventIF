package com.eventif.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequestDto(
        @NotBlank(message = "nome não pode estar vazio")
        @Size(max = 255, message = "nome deve ter no máximo 255 caracteres")
        String nome
) {
}
