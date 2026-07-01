package com.example.academia.Services;

import com.example.academia.Entities.Video;
import com.example.academia.Repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    public Video criar(Video video){
        return videoRepository.save(video);
    }

    public List<Video> listarTodos(){
        return videoRepository.findAll();
    }

    public List<Video> buscarPorNome(String nome){
        return videoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Video> listarPorCategoria(String categoria){
        return videoRepository.findByCategoria(categoria);
    }

    public Video buscarPorId(Long id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video não encontrado"));
    }

    public void deletar(Long id){
        videoRepository.deleteById(id);
    }

    public Video atualizar(Long id, Video videoAtualizado) {
        Video video = buscarPorId(id);
        video.setNome(videoAtualizado.getNome());
        video.setVideoUrl(videoAtualizado.getVideoUrl());
        video.setCategoria(videoAtualizado.getCategoria());
        return videoRepository.save(video);
    }
}
