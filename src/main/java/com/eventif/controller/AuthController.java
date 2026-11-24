package com.eventif.controller;

import com.eventif.dto.CadastroRequestDto;
import com.eventif.dto.LoginRequestDto;
import com.eventif.dto.UserResponseDto;
import com.eventif.model.Usuario;
import com.eventif.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequestDto dto) {
        List<ResponseCookie> cookies = userService.loginUsuario(dto.email(), dto.senha());
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookies.get(0).toString())
                .header(HttpHeaders.SET_COOKIE, cookies.get(1).toString())
                .build();
    }

    @PostMapping("/cadastro-participante")
    public ResponseEntity<Void> cadastroParticipante(@RequestBody @Valid CadastroRequestDto dto) {
        userService.cadastrarParticipante(dto.email(), dto.senha(), dto.nome());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest httpServletRequest) {
        List<ResponseCookie> cookies = userService.realizarLogout(httpServletRequest);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .header(HttpHeaders.SET_COOKIE, cookies.get(0).toString())
                .header(HttpHeaders.SET_COOKIE, cookies.get(1).toString())
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getUsuarioLogado(@AuthenticationPrincipal Usuario usuarioLogado) {
        UserResponseDto dto = UserResponseDto.toDto(usuarioLogado);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(HttpServletRequest httpServletRequest) {
        List<ResponseCookie> cookies = userService.refresh(httpServletRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookies.get(0).toString())
                .header(HttpHeaders.SET_COOKIE, cookies.get(1).toString())
                .build();
    }
}
