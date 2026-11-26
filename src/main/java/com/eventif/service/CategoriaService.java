package com.eventif.service;

import com.eventif.dto.CategoriaResponseDto;
import com.eventif.exception.RegraDeNegocioException;
import com.eventif.model.Categoria;
import com.eventif.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaResponseDto> listarCategorias() {
        return categoriaRepository.findAll().stream()
                .map(CategoriaResponseDto::toDto)
                .toList();
    }

    public CategoriaResponseDto buscarCategoriaPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
        return CategoriaResponseDto.toDto(categoria);
    }

    @Transactional
    public CategoriaResponseDto criarCategoria(String nome) {
        if (categoriaRepository.existsByNomeIgnoreCase(nome)) {
            throw new RegraDeNegocioException("Categoria já existe");
        }

        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        return CategoriaResponseDto.toDto(categoriaRepository.save(categoria));
    }

    @Transactional
    public CategoriaResponseDto atualizarCategoria(Long id, String nome) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        if (categoriaRepository.existsByNomeIgnoreCaseAndIdNot(nome, id)) {
            throw new RegraDeNegocioException("Categoria já existe");
        }

        categoria.setNome(nome);
        return CategoriaResponseDto.toDto(categoriaRepository.save(categoria));
    }

    @Transactional
    public void excluirCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        if (categoria.getEventos() != null && !categoria.getEventos().isEmpty()) {
            throw new RegraDeNegocioException("Não é possível excluir categoria com eventos vinculados");
        }

        categoriaRepository.delete(categoria);
    }
}
