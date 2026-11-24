package com.eventif.controller;

import com.eventif.dto.CadastroRequestDto;
import com.eventif.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organizador")
public class OrganizadorController {

    private final UserService userService;

    public OrganizadorController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Void> cadastroOrganizador(@RequestBody @Valid CadastroRequestDto dto) {
        userService.cadastrarOrganizador(dto.email(), dto.senha(), dto.nome());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
