package com.example.academia.Dtos;

public class ExercicioResponse {

    private Long id;
    private String nome;
    private int series;
    private int repeticoes;
    private int ordem;

    public ExercicioResponse() {}

    public ExercicioResponse(Long id, String nome, int series, int repeticoes, int ordem) {
        this.id = id;
        this.nome = nome;
        this.series = series;
        this.repeticoes = repeticoes;
        this.ordem = ordem;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getSeries() { return series; }
    public void setSeries(int series) { this.series = series; }

    public int getRepeticoes() { return repeticoes; }
    public void setRepeticoes(int repeticoes) { this.repeticoes = repeticoes; }

    public int getOrdem() { return ordem; }
    public void setOrdem(int ordem) { this.ordem = ordem; }
}
