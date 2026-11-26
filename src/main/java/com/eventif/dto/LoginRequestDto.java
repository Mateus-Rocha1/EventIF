package com.eventif.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @Email(message = "não está no formato válido de email")
        @NotBlank(message = "email não pode estar vazio")
        String email,

        @NotBlank(message = "senha não pode ser vazia")
        String senha
) {
}
