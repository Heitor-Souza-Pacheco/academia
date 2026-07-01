package com.example.academia.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * Traduz exceções em respostas HTTP coerentes.
 *
 * Sem este handler, qualquer RuntimeException lançada nos services virava um
 * 500 que o Spring reencaminhava para /error. Como a aplicação é STATELESS e o
 * JwtFilter (OncePerRequestFilter) não re-autentica no dispatch de erro, esse
 * /error caía no Http403ForbiddenEntryPoint e retornava 403 — fazendo o front
 * achar que a sessão expirou e deslogar o usuário indevidamente.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Erros de autenticação/autorização REAIS continuam como 401/403.
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuth(AuthenticationException ex) {
        return build(HttpStatus.UNAUTHORIZED, "Não autenticado.");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        return build(HttpStatus.FORBIDDEN, "Você não tem permissão para esta ação.");
    }

    // Regras de negócio (ex.: "Vídeo não encontrado", "Ficha não encontrada").
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntime(RuntimeException ex) {
        String msg = (ex.getMessage() != null && !ex.getMessage().isBlank())
                ? ex.getMessage()
                : "Não foi possível processar a requisição.";
        HttpStatus status = msg.toLowerCase().contains("não encontrad")
                ? HttpStatus.NOT_FOUND
                : HttpStatus.BAD_REQUEST;
        return build(status, msg);
    }

    private ResponseEntity<Map<String, Object>> build(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of(
                "status", status.value(),
                "message", message
        ));
    }
}
