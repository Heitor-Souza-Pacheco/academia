package com.example.academia.Dtos;

public class VideoResponse {

    private Long id;
    private String nome;
    private String videoUrl;
    private String categoria;

    public VideoResponse() {}

    public VideoResponse(Long id, String nome, String videoUrl, String categoria) {
        this.id = id;
        this.nome = nome;
        this.videoUrl = videoUrl;
        this.categoria = categoria;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
