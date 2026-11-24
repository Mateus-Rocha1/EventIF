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
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario user;

    private LocalDateTime expiresAt;

    private LocalDateTime sessionExpiresAt;

    public RefreshToken() {
    }

    public RefreshToken(Long id, UUID refreshToken, Usuario user, LocalDateTime expiresAt, LocalDateTime sessionExpiresAt) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.user = user;
        this.expiresAt = expiresAt;
        this.sessionExpiresAt = sessionExpiresAt;
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

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getSessionExpiresAt() {
        return sessionExpiresAt;
    }

    public void setSessionExpiresAt(LocalDateTime sessionExpiresAt) {
        this.sessionExpiresAt = sessionExpiresAt;
    }
}
