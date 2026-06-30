package com.example.academia.Services;

import com.example.academia.Entities.Role;
import com.example.academia.Entities.User;
import com.example.academia.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String registrar(String nome, String email, String senha) {

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email já cadastrado");
        }

        User user = new User();
        user.setNome(nome);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(senha));
        user.setRole(Role.USER);

        userRepository.save(user);

        return jwtService.gerarToken(user);
    }

    public String login(String email, String senha) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email ou senha inválidos"));

        if (!passwordEncoder.matches(senha, user.getPassword())) {
            throw new RuntimeException("Email ou senha inválidos");
        }

        return jwtService.gerarToken(user);
    }
}
