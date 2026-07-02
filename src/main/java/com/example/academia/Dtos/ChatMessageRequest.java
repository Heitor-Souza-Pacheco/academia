package com.example.academia.Dtos;

import jakarta.validation.constraints.Size;

public class ChatMessageRequest {

    private String role;      // "user" ou "assistant"

    @Size(max = 2000, message = "Mensagem muito longa (máximo 2000 caracteres)")
    private String conteudo;

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
}
