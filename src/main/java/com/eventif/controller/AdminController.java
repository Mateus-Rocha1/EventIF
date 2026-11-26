package com.eventif.controller;

import com.eventif.dto.CadastroRequestDto;
import com.eventif.dto.CategoriaRequestDto;
import com.eventif.dto.CategoriaResponseDto;
import com.eventif.service.CategoriaService;
import com.eventif.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UsuarioService usuarioService;
    private final CategoriaService categoriaService;

    public AdminController(UsuarioService usuarioService, CategoriaService categoriaService) {
        this.usuarioService = usuarioService;
        this.categoriaService = categoriaService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Void> cadastroAdmin(@RequestBody @Valid CadastroRequestDto dto) {
        usuarioService.cadastrarAdmin(dto.email(), dto.senha(), dto.nome());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaResponseDto>> listarCategorias() {
        List<CategoriaResponseDto> categorias = categoriaService.listarCategorias();
        return ResponseEntity.status(HttpStatus.OK).body(categorias);
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<CategoriaResponseDto> buscarCategoriaPorId(@PathVariable Long id) {
        CategoriaResponseDto categoria = categoriaService.buscarCategoriaPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(categoria);
    }

    @PostMapping("/categorias")
    public ResponseEntity<CategoriaResponseDto> criarCategoria(@RequestBody @Valid CategoriaRequestDto dto) {
        CategoriaResponseDto criada = categoriaService.criarCategoria(dto.nome());
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<CategoriaResponseDto> atualizarCategoria(@PathVariable Long id, @RequestBody @Valid CategoriaRequestDto dto) {
        CategoriaResponseDto atualizada = categoriaService.atualizarCategoria(id, dto.nome());
        return ResponseEntity.status(HttpStatus.OK).body(atualizada);
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> excluirCategoria(@PathVariable Long id) {
        categoriaService.excluirCategoria(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
