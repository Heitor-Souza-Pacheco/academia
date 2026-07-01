package com.example.academia.Controllers;

import com.example.academia.Entities.Video;
import com.example.academia.Services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping
    public ResponseEntity<List<Video>> listarTodos() {
        return ResponseEntity.ok(videoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.buscarPorId(id));
    }

    @GetMapping("/busca")
    public ResponseEntity<List<Video>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(videoService.buscarPorNome(nome));
    }

    @PostMapping
    public ResponseEntity<Video> criar(@RequestBody Video video) {
        return ResponseEntity.ok(videoService.criar(video));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        videoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Video> atualizar(@PathVariable Long id, @RequestBody Video videoAtualizado) {
        return ResponseEntity.ok(videoService.atualizar(id, videoAtualizado));
    }
}
