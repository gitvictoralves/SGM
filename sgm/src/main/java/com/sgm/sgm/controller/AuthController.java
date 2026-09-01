package com.sgm.sgm.controller;

import com.sgm.sgm.dto.CadastroRequest;
import com.sgm.sgm.dto.LoginRequest;
import com.sgm.sgm.dto.LoginResponse;
import com.sgm.sgm.model.Usuario;
import com.sgm.sgm.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario cadastrar(@Valid @RequestBody CadastroRequest request) {
        return authService.cadastrar(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}