package com.example.academia.Controllers;

import com.example.academia.Dtos.LoginRequest;
import com.example.academia.Dtos.RegisterRequest;
import com.example.academia.Services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registrar(@RequestBody RegisterRequest request){
        String mensagem = authService.registrar(request.getNome(), request.getEmail(), request.getSenha());
        return ResponseEntity.status(HttpStatus.CREATED).body(mensagem);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request){
        String token = authService.login(request.getEmail(), request.getSenha());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verificarEmail(@RequestParam String token){
        authService.verificarEmail(token);
        String html = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Email Verificado</title>
                    <style>
                        * { margin: 0; padding: 0; box-sizing: border-box; }
                        body {
                            font-family: 'Segoe UI', sans-serif;
                            background: #1a1a1a;
                            color: #fff;
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            min-height: 100vh;
                        }
                        .card {
                            background: #2a2a2a;
                            border-radius: 16px;
                            padding: 48px;
                            text-align: center;
                            max-width: 420px;
                            box-shadow: 0 20px 60px rgba(0,0,0,0.5);
                        }
                        .icon {
                            font-size: 64px;
                            margin-bottom: 24px;
                        }
                        h1 {
                            font-size: 24px;
                            margin-bottom: 12px;
                            color: #e74c3c;
                        }
                        p {
                            color: #aaa;
                            margin-bottom: 32px;
                            line-height: 1.5;
                        }
                        a {
                            display: inline-block;
                            background: #e74c3c;
                            color: #fff;
                            text-decoration: none;
                            padding: 12px 32px;
                            border-radius: 8px;
                            font-weight: bold;
                            transition: background 0.2s;
                        }
                        a:hover { background: #c0392b; }
                    </style>
                </head>
                <body>
                    <div class="card">
                        <div class="icon">✅</div>
                        <h1>Email verificado!</h1>
                        <p>Sua conta foi ativada com sucesso. Agora você pode fazer login e acessar suas fichas de treino.</p>
                        <a href="http://localhost:5500">Ir para o login</a>
                    </div>
                </body>
                </html>
                """;
        return ResponseEntity.ok().header("Content-Type", "text/html").body(html);
    }
}
