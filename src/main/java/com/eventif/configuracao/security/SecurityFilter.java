package com.eventif.configuracao.security;

import com.eventif.model.User;
import com.eventif.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final TokenCookieService tokenCookieService;

    public SecurityFilter(TokenJWTService tokenJWTService, UserRepository userRepository, TokenCookieService tokenCookieService) {
        this.tokenJWTService = tokenJWTService;
        this.userRepository = userRepository;
        this.tokenCookieService = tokenCookieService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenJWT = tokenCookieService.lerTokenDoCookie(request);

        if (tokenJWT != null) {
            try {
                String email = tokenJWTService.getSubject(tokenJWT);
                User usuario = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
