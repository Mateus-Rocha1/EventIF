package com.eventif.service;

import com.eventif.exception.RegraDeNegocioException;
import com.eventif.model.RefreshToken;
import com.eventif.model.Usuario;
import com.eventif.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public RefreshToken criarRefreshTokenNoLogin(Usuario usuario) {

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setRefreshToken(UUID.randomUUID());
        refreshToken.setUsuario(usuario);
        refreshToken.setExpiraEm(LocalDateTime.now().plus(Duration.ofHours(1)));
        refreshToken.setSessaoExpiraEm(LocalDateTime.now().plus(Duration.ofHours(8)));

        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    public RefreshToken trocaORefreshToken(UUID refreshTokenRecebido) {

        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(refreshTokenRecebido).orElseThrow(() -> new RegraDeNegocioException("Erro ao renovar token"));

        if (LocalDateTime.now().isAfter(refreshToken.getExpiraEm()) || LocalDateTime.now().isAfter(refreshToken.getSessaoExpiraEm())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RegraDeNegocioException("Sessão expirada");
        }

        refreshToken.setExpiraEm(LocalDateTime.now().plusHours(1));
        refreshToken.setRefreshToken(UUID.randomUUID());
        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    @Transactional
    public void removerRefreshTokenNoLogout(UUID refreshToken) {
        refreshTokenRepository.findByRefreshToken(refreshToken).ifPresent(refreshTokenRepository::delete);
    }
}
