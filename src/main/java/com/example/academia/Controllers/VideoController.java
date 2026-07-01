package com.example.academia.Controllers;

import com.example.academia.Dtos.VideoRequest;
import com.example.academia.Dtos.VideoResponse;
import com.example.academia.Services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping
    public ResponseEntity<List<VideoResponse>> listarTodos() {
        return ResponseEntity.ok(videoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.buscarPorId(id));
    }

    @GetMapping("/busca")
    public ResponseEntity<List<VideoResponse>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(videoService.buscarPorNome(nome));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<VideoResponse>> listarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(videoService.listarPorCategoria(categoria));
    }

    @PostMapping
    public ResponseEntity<VideoResponse> criar(@RequestBody VideoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(videoService.criar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoResponse> atualizar(@PathVariable Long id, @RequestBody VideoRequest request) {
        return ResponseEntity.ok(videoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        videoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
