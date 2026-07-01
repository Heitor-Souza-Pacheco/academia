package com.example.academia.Services;

import com.example.academia.Dtos.VideoRequest;
import com.example.academia.Dtos.VideoResponse;
import com.example.academia.Entities.Video;
import com.example.academia.Repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    public VideoResponse criar(VideoRequest request) {
        Video video = new Video();
        video.setNome(request.getNome());
        video.setVideoUrl(request.getVideoUrl());
        video.setCategoria(request.getCategoria());
        return toResponse(videoRepository.save(video));
    }

    public List<VideoResponse> listarTodos() {
        return videoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<VideoResponse> buscarPorNome(String nome) {
        return videoRepository.findByNomeContainingIgnoreCase(nome).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<VideoResponse> listarPorCategoria(String categoria) {
        return videoRepository.findByCategoria(categoria).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public VideoResponse buscarPorId(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vídeo não encontrado"));
        return toResponse(video);
    }

    public void deletar(Long id) {
        videoRepository.deleteById(id);
    }

    public VideoResponse atualizar(Long id, VideoRequest request) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vídeo não encontrado"));
        video.setNome(request.getNome());
        video.setVideoUrl(request.getVideoUrl());
        video.setCategoria(request.getCategoria());
        return toResponse(videoRepository.save(video));
    }

    private VideoResponse toResponse(Video video) {
        return new VideoResponse(video.getId(), video.getNome(), video.getVideoUrl(), video.getCategoria());
    }
}
