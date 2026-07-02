package com.example.academia.Services;

import com.example.academia.Entities.Role;
import com.example.academia.Entities.User;
import com.example.academia.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;

    public String registrar(String nome, String email, String senha) {

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email já cadastrado");
        }

        // Gera token de verificação
        String tokenVerificacao = UUID.randomUUID().toString();

        User user = new User();
        user.setNome(nome);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(senha));
        user.setRole(Role.USER);
        user.setVerficado(false);
        user.setTokenVerificacao(tokenVerificacao);
        user.setTokenExpiracao(LocalDateTime.now().plusHours(24));

        userRepository.save(user);

        // Envia email de verificação
        emailService.enviarEmailVerificacao(email, tokenVerificacao);

        return "Verifique seu email para ativar sua conta.";
    }

    public String login(String email, String senha) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email ou senha inválidos"));

        if (!passwordEncoder.matches(senha, user.getPassword())) {
            throw new RuntimeException("Email ou senha inválidos");
        }

        if (!user.isVerficado()) {
            throw new RuntimeException("Email não verificado. Verifique sua caixa de entrada.");
        }

        return jwtService.gerarToken(user);
    }

    public void verificarEmail(String token) {
        User user = userRepository.findByTokenVerificacao(token)
                .orElseThrow(() -> new RuntimeException("Token inválido ou já utilizado"));

        if (user.getTokenExpiracao() != null && user.getTokenExpiracao().isBefore(LocalDateTime.now())) {
            // Token expirado — remove o usuário não verificado pra permitir novo cadastro
            userRepository.delete(user);
            throw new RuntimeException("Link de verificação expirado. Faça o cadastro novamente.");
        }

        user.setVerficado(true);
        user.setTokenVerificacao(null);
        user.setTokenExpiracao(null);
        userRepository.save(user);
    }
}
