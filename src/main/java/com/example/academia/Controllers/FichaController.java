package com.example.academia.Controllers;

import com.example.academia.Entities.Ficha;
import com.example.academia.Services.FichaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fichas")
@RequiredArgsConstructor
public class FichaController {

    private final FichaService fichaService;

    @GetMapping
    public ResponseEntity<List<Ficha>> listarTodas(){
        return ResponseEntity.ok(fichaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ficha> buscarPorId(Long id){
        return ResponseEntity.ok(fichaService.buscarPorId(id));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Ficha>> listarPorCategoria(String categoria){
        return ResponseEntity.ok(fichaService.listarPorCategoria(categoria));
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<Ficha>> buscarPorTitulo(String titulo){
        return ResponseEntity.ok(fichaService.buscarPorTitulo(titulo));
    }

    //admin
    @PostMapping
    public ResponseEntity<Ficha> cadastrar(Ficha ficha){
        return ResponseEntity.ok(fichaService.criarFicha(ficha));
    }

    //admin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(Long id){
        fichaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ficha> atualizar(@RequestBody Ficha fichaNova, Long id){
        return ResponseEntity.ok(fichaService.atualizarFicha(fichaNova, id));
    }
}
