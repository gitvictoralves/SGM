package com.sgm.sgm.service;

import com.sgm.sgm.dto.CadastroRequest;
import com.sgm.sgm.dto.LoginRequest;
import com.sgm.sgm.dto.LoginResponse;
import com.sgm.sgm.exception.RegraNegocioException;
import com.sgm.sgm.model.Usuario;
import com.sgm.sgm.repository.UsuarioRepository;
import com.sgm.sgm.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public Usuario cadastrar(CadastroRequest request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegraNegocioException("E-mail já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));

        return usuarioRepository.save(usuario);
    }

    public LoginResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
            );
        } catch (BadCredentialsException ex) {
            throw new RegraNegocioException("E-mail ou senha inválidos");
        }

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail()).get();
        String token = jwtUtil.gerarToken(usuario.getEmail());

        return new LoginResponse(token, usuario.getEmail(), usuario.getNome());
    }
}