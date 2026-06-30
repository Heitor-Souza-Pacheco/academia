package com.example.academia.Dtos;

import java.util.List;

public class FichaRequest {

    private String titulo;
    private String descricao;
    private String categoria;
    private List<ExercicioRequest> exercicios;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public List<ExercicioRequest> getExercicios() { return exercicios; }
    public void setExercicios(List<ExercicioRequest> exercicios) { this.exercicios = exercicios; }
}
