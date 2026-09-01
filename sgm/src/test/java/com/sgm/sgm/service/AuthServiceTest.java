package com.sgm.sgm.service;

import com.sgm.sgm.dto.CadastroRequest;
import com.sgm.sgm.dto.LoginRequest;
import com.sgm.sgm.exception.RegraNegocioException;
import com.sgm.sgm.model.Usuario;
import com.sgm.sgm.repository.UsuarioRepository;
import com.sgm.sgm.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @Test
    void login_deveLancarRegraNegocioException_quandoSenhaEstiverIncorreta() {
        LoginRequest request = new LoginRequest();
        request.setEmail("usuario@teste.com");
        request.setSenha("senhaErrada");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Credenciais inválidas"));

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(RegraNegocioException.class)
                .hasMessageContaining("E-mail ou senha inválidos");

        verify(jwtUtil, never()).gerarToken(anyString());
    }

    @Test
    void cadastrar_deveLancarRegraNegocioException_quandoEmailJaCadastrado() {
        CadastroRequest request = new CadastroRequest();
        request.setNome("Maria Souza");
        request.setEmail("maria@teste.com");
        request.setSenha("123456");

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1L);
        usuarioExistente.setEmail("maria@teste.com");

        when(usuarioRepository.findByEmail("maria@teste.com"))
                .thenReturn(Optional.of(usuarioExistente));

        assertThatThrownBy(() -> authService.cadastrar(request))
                .isInstanceOf(RegraNegocioException.class)
                .hasMessageContaining("E-mail já cadastrado");

        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}