package com.example.academia.Controllers;

import com.example.academia.Dtos.ChatRequest;
import com.example.academia.Dtos.ChatResponse;
import com.example.academia.Services.AssistenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/assistente")
@RequiredArgsConstructor
public class AssistenteController {

    private final AssistenteService assistenteService;

    @PostMapping
    public ResponseEntity<?> conversar(@Valid @RequestBody ChatRequest request) {
        try {
            String resposta = assistenteService.responder(request.getMensagens());
            return ResponseEntity.ok(new ChatResponse(resposta));
        } catch (RuntimeException e) {
            // 503 (não 401/403) — o front mostra a mensagem sem deslogar o usuário.
            String msg = e.getMessage() != null ? e.getMessage() : "Erro ao consultar o assistente.";
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of("message", msg));
        }
    }
}
