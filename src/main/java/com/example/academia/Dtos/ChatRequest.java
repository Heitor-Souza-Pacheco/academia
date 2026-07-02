package com.example.academia.Dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ChatRequest {

    @NotNull(message = "Mensagens são obrigatórias")
    @Size(min = 1, max = 30, message = "Envie entre 1 e 30 mensagens por vez")
    private List<ChatMessageRequest> mensagens;

    public List<ChatMessageRequest> getMensagens() { return mensagens; }
    public void setMensagens(List<ChatMessageRequest> mensagens) { this.mensagens = mensagens; }
}
