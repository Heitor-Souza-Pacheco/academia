package com.example.academia.Controllers;

import com.example.academia.Dtos.LoginRequest;
import com.example.academia.Dtos.RegisterRequest;
import com.example.academia.Services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registrar(@RequestBody RegisterRequest request){
        String token = authService.registrar(request.getNome(), request.getEmail(), request.getSenha());
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request){
        String token = authService.login(request.getEmail(), request.getSenha());
        return ResponseEntity.ok(token);
    }
}
