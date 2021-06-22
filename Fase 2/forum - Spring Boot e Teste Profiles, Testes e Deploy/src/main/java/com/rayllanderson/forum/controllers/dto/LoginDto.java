package com.rayllanderson.forum.controllers.dto;

public class LoginDto {
    private final String token;
    private final String tipoDeAutenticacao;

    public LoginDto(String token, String tipoDeAutenticacao) {
        this.token = token;
        this.tipoDeAutenticacao = tipoDeAutenticacao;
    }

    public LoginDto(String token) {
        this.token = token;
        this.tipoDeAutenticacao = "Bearer";
    }

    public String getToken() {
        return token;
    }

    public String getTipoDeAutenticacao() {
        return tipoDeAutenticacao;
    }
}
