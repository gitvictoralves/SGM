package com.sgm.sgm.dto;

public class LoginResponse {

    private String token;
    private String email;
    private String nome;

    public LoginResponse(String token, String email, String nome) {
        this.token = token;
        this.email = email;
        this.nome = nome;
    }

    public String getToken() { return token; }
    public String getEmail() { return email; }
    public String getNome() { return nome; }
}