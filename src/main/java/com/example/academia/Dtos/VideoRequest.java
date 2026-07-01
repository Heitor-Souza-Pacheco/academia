package com.example.academia.Dtos;

public class VideoRequest {

    private String nome;
    private String videoUrl;
    private String categoria;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
