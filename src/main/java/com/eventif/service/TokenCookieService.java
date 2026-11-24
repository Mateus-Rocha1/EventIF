package com.eventif.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class TokenCookieService {
    private static final String NOME_COOKIE_TOKEN = "jwt_token_eventif";
    private static final String NOME_COOKIE_REFRESH_TOKEN = "jwt_refresh_token_eventif";

    public ResponseCookie gerarCookieComToken(String tokenJWT, Duration duracao) {
        return ResponseCookie.from(NOME_COOKIE_TOKEN, tokenJWT)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(duracao)
                .sameSite("Lax")
                .build();
    }

    public String lerTokenDoCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(NOME_COOKIE_TOKEN)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public ResponseCookie limparTokenDoCookie() {
        return ResponseCookie.from(NOME_COOKIE_TOKEN, "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();
    }

    public ResponseCookie gerarCookieComRefreshToken(String tokenJWT, Duration duracao) {
        return ResponseCookie.from(NOME_COOKIE_REFRESH_TOKEN, tokenJWT)
                .httpOnly(true)
                .secure(false)
                .path("/auth")
                .maxAge(duracao)
                .sameSite("Lax")
                .build();
    }

    public String lerRefreshTokenDoCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(NOME_COOKIE_REFRESH_TOKEN)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public ResponseCookie limparRefreshTokenDoCookie() {
        return ResponseCookie.from(NOME_COOKIE_REFRESH_TOKEN, "")
                .httpOnly(true)
                .secure(false)
                .path("/auth")
                .maxAge(0)
                .sameSite("Lax")
                .build();
    }
}
