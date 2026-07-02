package com.example.academia.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarEmailVerificacao(String destinatario, String token){
        String link = "http://localhost:8080/auth/verify?token=" + token;

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom("Academia Cranium <heitor.pacheco@araujo.com.br>");
        mensagem.setTo(destinatario);
        mensagem.setSubject("Verifique seu email");
        mensagem.setText("Clique no link abaixo para verificar seu email:\n\n" + link + "\n\nSe você não criou esta conta, ignore este email.");

        mailSender.send(mensagem);
    }
}
