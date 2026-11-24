package com.eventif.configuracao.security;

import com.eventif.model.Usuario;
import com.eventif.repository.UsuarioRepository;
import com.eventif.service.TokenCookieService;
import com.eventif.service.TokenJWTService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenJWTService tokenJWTService;
    private final UsuarioRepository usuarioRepository;
    private final TokenCookieService tokenCookieService;

    public SecurityFilter(TokenJWTService tokenJWTService, UsuarioRepository usuarioRepository, TokenCookieService tokenCookieService) {
        this.tokenJWTService = tokenJWTService;
        this.usuarioRepository = usuarioRepository;
        this.tokenCookieService = tokenCookieService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenJWT = tokenCookieService.lerTokenDoCookie(request);

        if (tokenJWT != null) {
            try {
                String email = tokenJWTService.getSubject(tokenJWT);
                Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

                if (usuario.isAtivo()) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
