package com.example.academia.Controllers;

import com.example.academia.Dtos.LoginRequest;
import com.example.academia.Dtos.RegisterRequest;
import com.example.academia.Services.AuthService;
import jakarta.validation.Valid;
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
    public ResponseEntity<String> registrar(@Valid @RequestBody RegisterRequest request){
        String mensagem = authService.registrar(request.getNome(), request.getEmail(), request.getSenha());
        return ResponseEntity.status(HttpStatus.CREATED).body(mensagem);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request){
        String token = authService.login(request.getEmail(), request.getSenha());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verificarEmail(@RequestParam String token){
        try {
            authService.verificarEmail(token);
            String html = paginaVerificacao(
                    "badge--ok", "✓",
                    "Email verificado!",
                    "Sua conta foi ativada com sucesso. Agora você já pode fazer login e acessar suas fichas de treino."
            );
            return ResponseEntity.ok()
                    .header("Content-Type", "text/html; charset=UTF-8")
                    .body(html);
        } catch (RuntimeException e) {
            String html = paginaVerificacao(
                    "badge--err", "✕",
                    "Link inválido",
                    "Este link de verificação é inválido ou já foi utilizado. Se sua conta já está ativa, é só entrar; caso contrário, faça o cadastro novamente."
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "text/html; charset=UTF-8")
                    .body(html);
        }
    }

    // Endereço do front (tela de login). Ajuste aqui se o front mudar de porta/host.
    private static final String FRONT_URL = "http://localhost:5500";

    // Página de retorno do link de verificação, no tema da Cranium.
    // Usa .replace (e não .formatted) de propósito: o CSS contém '%' literais.
    private String paginaVerificacao(String badgeClass, String icone, String titulo, String mensagem) {
        return """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Cranium — Verificação de email</title>
                    <link rel="preconnect" href="https://fonts.googleapis.com">
                    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                    <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@600;700&family=Inter:wght@400;500;600&display=swap" rel="stylesheet">
                    <style>
                        :root {
                            --red: #e11d2a; --red-dark: #b3141f; --red-glow: rgba(225,29,42,.35);
                            --black: #0b0b0d; --panel: #141417; --panel-2: #1c1c21; --line: #2e2e35;
                            --white: #fff; --text: #f3f3f5; --muted: #a0a0aa; --muted-2: #6c6c76;
                        }
                        * { margin: 0; padding: 0; box-sizing: border-box; }
                        body {
                            font-family: 'Inter', system-ui, -apple-system, sans-serif;
                            background:
                                radial-gradient(1200px 600px at 50% -10%, rgba(225,29,42,.18), transparent 60%),
                                var(--black);
                            color: var(--text);
                            min-height: 100vh;
                            display: flex; align-items: center; justify-content: center;
                            padding: 24px;
                            -webkit-font-smoothing: antialiased;
                        }
                        .card {
                            position: relative;
                            background: var(--panel);
                            border: 1px solid var(--line);
                            border-radius: 16px;
                            padding: 48px 40px;
                            max-width: 460px; width: 100%;
                            text-align: center;
                            box-shadow: 0 18px 50px rgba(0,0,0,.55);
                            overflow: hidden;
                        }
                        .card__stripe {
                            position: absolute; top: 0; left: 0; right: 0; height: 5px;
                            background: linear-gradient(90deg, var(--red), var(--red-dark));
                        }
                        .brand {
                            display: flex; align-items: center; justify-content: center;
                            gap: 10px; margin-bottom: 30px;
                        }
                        .brand__mark {
                            display: grid; place-items: center; width: 44px; height: 44px;
                            font-family: 'Oswald', sans-serif; font-weight: 700; font-size: 26px; color: #fff;
                            background: linear-gradient(135deg, var(--red), var(--red-dark));
                            border-radius: 10px; box-shadow: 0 6px 18px var(--red-glow);
                        }
                        .brand__name {
                            font-family: 'Oswald', sans-serif; font-weight: 700;
                            letter-spacing: 4px; font-size: 22px; color: #fff;
                        }
                        .badge {
                            width: 86px; height: 86px; margin: 0 auto 24px;
                            display: grid; place-items: center;
                            border-radius: 50%; font-size: 42px; line-height: 1; font-weight: 700;
                        }
                        .badge--ok {
                            background: linear-gradient(135deg, var(--red), var(--red-dark));
                            color: #fff; box-shadow: 0 10px 30px var(--red-glow);
                        }
                        .badge--err {
                            background: var(--panel-2); border: 1px solid var(--line); color: var(--red);
                        }
                        h1 {
                            font-family: 'Oswald', sans-serif; font-weight: 700;
                            text-transform: uppercase; letter-spacing: 1px;
                            font-size: 26px; color: var(--white); margin-bottom: 12px;
                        }
                        p { color: var(--muted); font-size: 15px; line-height: 1.6; margin-bottom: 32px; }
                        .btn {
                            display: inline-block; text-decoration: none;
                            background: var(--red); color: #fff; font-weight: 600; font-size: 15px;
                            padding: 14px 34px; border-radius: 10px;
                            box-shadow: 0 8px 20px var(--red-glow);
                            transition: background .18s ease, transform .18s ease;
                        }
                        .btn:hover { background: var(--red-dark); transform: translateY(-1px); }
                        .footnote { margin-top: 26px; color: var(--muted-2); font-size: 12px; letter-spacing: .5px; }
                    </style>
                </head>
                <body>
                    <div class="card">
                        <div class="card__stripe"></div>
                        <div class="brand">
                            <span class="brand__mark">C</span>
                            <span class="brand__name">CRANIUM</span>
                        </div>
                        <div class="badge %BADGE%">%ICONE%</div>
                        <h1>%TITULO%</h1>
                        <p>%MENSAGEM%</p>
                        <a class="btn" href="%FRONT%">Ir para o login</a>
                        <div class="footnote">CRANIUM ACADEMIA</div>
                    </div>
                </body>
                </html>
                """
                .replace("%BADGE%", badgeClass)
                .replace("%ICONE%", icone)
                .replace("%TITULO%", titulo)
                .replace("%MENSAGEM%", mensagem)
                .replace("%FRONT%", FRONT_URL);
    }
}
