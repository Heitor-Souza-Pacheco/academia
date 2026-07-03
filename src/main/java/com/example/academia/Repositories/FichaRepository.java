package com.example.academia.Repositories;

import com.example.academia.Entities.Ficha;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FichaRepository extends JpaRepository<Ficha, Long> {

    @EntityGraph(attributePaths = {"exercicios", "exercicios.video"})
    List<Ficha> findAll();

    @EntityGraph(attributePaths = {"exercicios", "exercicios.video"})
    Optional<Ficha> findById(Long id);

    @EntityGraph(attributePaths = {"exercicios", "exercicios.video"})
    List<Ficha> findByCategoria(String categoria);

    @EntityGraph(attributePaths = {"exercicios", "exercicios.video"})
    List<Ficha> findByTituloContainingIgnoreCase(String titulo);
}
