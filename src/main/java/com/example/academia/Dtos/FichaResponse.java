package com.example.academia.Dtos;

import java.time.LocalDate;
import java.util.List;

public class FichaResponse {

    private Long id;
    private String titulo;
    private String descricao;
    private String categoria;
    private LocalDate data;
    private List<ExercicioResponse> exercicios;

    public FichaResponse() {}

    public FichaResponse(Long id, String titulo, String descricao, String categoria, LocalDate data, List<ExercicioResponse> exercicios) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.data = data;
        this.exercicios = exercicios;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public List<ExercicioResponse> getExercicios() { return exercicios; }
    public void setExercicios(List<ExercicioResponse> exercicios) { this.exercicios = exercicios; }
}
