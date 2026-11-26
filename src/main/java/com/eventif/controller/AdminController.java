package com.eventif.controller;

import com.eventif.dto.CadastroRequestDto;
import com.eventif.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UsuarioService usuarioService;

    public AdminController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Void> cadastroAdmin(@RequestBody @Valid CadastroRequestDto dto) {
        usuarioService.cadastrarAdmin(dto.email(), dto.senha(), dto.nome());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
