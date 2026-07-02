package com.example.academia.Dtos;

public class ChatMessageRequest {

    private String role;      // "user" ou "assistant"
    private String conteudo;

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
}
