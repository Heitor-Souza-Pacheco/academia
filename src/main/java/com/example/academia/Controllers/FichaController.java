package com.example.academia.Controllers;

import com.example.academia.Dtos.FichaRequest;
import com.example.academia.Dtos.FichaResponse;
import com.example.academia.Services.FichaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fichas")
@RequiredArgsConstructor
public class FichaController {

    private final FichaService fichaService;

    @GetMapping
    public ResponseEntity<List<FichaResponse>> listarTodas() {
        return ResponseEntity.ok(fichaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FichaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(fichaService.buscarPorId(id));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<FichaResponse>> listarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(fichaService.listarPorCategoria(categoria));
    }

    @GetMapping("/busca")
    public ResponseEntity<List<FichaResponse>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(fichaService.buscarPorTitulo(titulo));
    }

    @PostMapping
    public ResponseEntity<FichaResponse> cadastrar(@RequestBody FichaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fichaService.criarFicha(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FichaResponse> atualizar(@PathVariable Long id, @RequestBody FichaRequest request) {
        return ResponseEntity.ok(fichaService.atualizarFicha(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        fichaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
