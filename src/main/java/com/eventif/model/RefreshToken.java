package com.eventif.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID refreshToken;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private LocalDateTime expiraEm;

    private LocalDateTime sessaoExpiraEm;

    public RefreshToken() {
    }

    public RefreshToken(Long id, UUID refreshToken, Usuario usuario, LocalDateTime expiraEm, LocalDateTime sessaoExpiraEm) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.usuario = usuario;
        this.expiraEm = expiraEm;
        this.sessaoExpiraEm = sessaoExpiraEm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(UUID refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getExpiraEm() {
        return expiraEm;
    }

    public void setExpiraEm(LocalDateTime expiraEm) {
        this.expiraEm = expiraEm;
    }

    public LocalDateTime getSessaoExpiraEm() {
        return sessaoExpiraEm;
    }

    public void setSessaoExpiraEm(LocalDateTime sessaoExpiraEm) {
        this.sessaoExpiraEm = sessaoExpiraEm;
    }
}
