package com.eventif.service;

import com.eventif.exception.RegraDeNegocioException;
import com.eventif.model.Perfil;
import com.eventif.model.RefreshToken;
import com.eventif.model.Usuario;
import com.eventif.repository.PerfilRepository;
import com.eventif.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder encoder;
    private final TokenCookieService tokenCookieService;
    private final TokenJWTService tokenJWTService;
    private final RefreshTokenService refreshTokenService;

    public UserService(UserRepository userRepository, PerfilRepository perfilRepository, PasswordEncoder encoder, TokenCookieService tokenCookieService, TokenJWTService tokenJWTService, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.perfilRepository = perfilRepository;
        this.encoder = encoder;
        this.tokenCookieService = tokenCookieService;
        this.tokenJWTService = tokenJWTService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    public List<ResponseCookie> loginUsuario(String email, String senha) {

        Usuario usuario = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("usuário não encontrado"));
        List<ResponseCookie> cookies = new ArrayList<>();

        if (!usuario.isAtivo()) {
            throw new RegraDeNegocioException("Usuário inativo");
        }

        if (!encoder.matches(senha, usuario.getPassword())) {
            throw new RegraDeNegocioException("Senha inválida");
        } else {

            String token = tokenJWTService.generateToken(usuario, Duration.ofMinutes(15));
            cookies.add(tokenCookieService.gerarCookieComToken(token, Duration.ofMinutes(15)));

            RefreshToken refreshToken = refreshTokenService.criarRefreshTokenNoLogin(usuario);
            cookies.add(tokenCookieService.gerarCookieComRefreshToken(refreshToken.getRefreshToken().toString(), Duration.ofHours(1)));

            return cookies;
        }
    }

    public void cadastrarParticipante(String email, String senha, String nome) {
        Optional<Usuario> possivelUsuario = userRepository.findByEmail(email);

        if (possivelUsuario.isPresent()) {
            throw new RegraDeNegocioException("Usuário já existe");
        }

        Perfil perfil = perfilRepository.findByNome("ROLE_PARTICIPANTE").orElseThrow(() -> new EntityNotFoundException("Perfil ROLE_PARTICIPANTE não encontrado"));

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setNome(nome);
        usuario.setSenha(encoder.encode(senha));
        usuario.setAtivo(true);
        usuario.setPerfis(List.of(perfil));

        userRepository.save(usuario);
    }

    public void cadastrarOrganizador(String email, String senha, String nome) {
        Optional<Usuario> possivelUsuario = userRepository.findByEmail(email);

        if (possivelUsuario.isPresent()) {
            throw new RegraDeNegocioException("Usuário já existe");
        }

        Perfil perfil = perfilRepository.findByNome("ROLE_ORGANIZADOR").orElseThrow(() -> new EntityNotFoundException("Perfil ROLE_ORGANIZADOR não encontrado"));

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setNome(nome);
        usuario.setSenha(encoder.encode(senha));
        usuario.setAtivo(true);
        usuario.setPerfis(List.of(perfil));

        userRepository.save(usuario);
    }

    public void cadastrarAdmin(String email, String senha, String nome) {
        Optional<Usuario> possivelUsuario = userRepository.findByEmail(email);

        if (possivelUsuario.isPresent()) {
            throw new RegraDeNegocioException("Usuário já existe");
        }

        Perfil perfil = perfilRepository.findByNome("ROLE_ADMIN").orElseThrow(() -> new EntityNotFoundException("Perfil ROLE_ADMIN não encontrado"));

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setNome(nome);
        usuario.setSenha(encoder.encode(senha));
        usuario.setAtivo(true);
        usuario.setPerfis(List.of(perfil));

        userRepository.save(usuario);
    }

    public List<ResponseCookie> refresh(HttpServletRequest httpServletRequest) {

        String refreshTokenString = tokenCookieService.lerRefreshTokenDoCookie(httpServletRequest);

        if (refreshTokenString == null) {
            throw new RegraDeNegocioException("Refresh token não encontrado");
        }

        RefreshToken novoRefreshToken = refreshTokenService.trocaORefreshToken(UUID.fromString(refreshTokenString));
        ResponseCookie cookieRefreshToken = tokenCookieService.gerarCookieComRefreshToken(novoRefreshToken.getRefreshToken().toString(), Duration.ofHours(1));

        String acessToken = tokenJWTService.generateToken(novoRefreshToken.getUser(), Duration.ofMinutes(15));
        ResponseCookie cookieAcessToken = tokenCookieService.gerarCookieComToken(acessToken, Duration.ofMinutes(15));

        List<ResponseCookie> cookies = new ArrayList<>();
        cookies.add(cookieAcessToken);
        cookies.add(cookieRefreshToken);
        return cookies;
    }

    @Transactional
    public List<ResponseCookie> realizarLogout(HttpServletRequest httpServletRequest) {
        String refreshTokenString = tokenCookieService.lerRefreshTokenDoCookie(httpServletRequest);
        if (refreshTokenString != null) {
            refreshTokenService.removerRefreshTokenNoLogout(UUID.fromString(refreshTokenString));
        }

        ResponseCookie cookieAcessTokenLimpo = tokenCookieService.limparTokenDoCookie();
        ResponseCookie cookieRefreshTokenLimpo = tokenCookieService.limparRefreshTokenDoCookie();

        List<ResponseCookie> cookies = new ArrayList<>();
        cookies.add(cookieAcessTokenLimpo);
        cookies.add(cookieRefreshTokenLimpo);
        return cookies;
    }
}
