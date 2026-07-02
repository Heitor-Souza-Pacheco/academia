package com.example.academia.Dtos;

import java.util.List;

public class ChatRequest {

    private List<ChatMessageRequest> mensagens;

    public List<ChatMessageRequest> getMensagens() { return mensagens; }
    public void setMensagens(List<ChatMessageRequest> mensagens) { this.mensagens = mensagens; }
}
