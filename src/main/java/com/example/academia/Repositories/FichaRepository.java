package com.example.academia.Repositories;

import com.example.academia.Entities.Ficha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FichaRepository extends JpaRepository<Ficha, Long> {

    List<Ficha> findByCategoria(String categoria);
    List<Ficha> findByTituloContaingIgnoreCase(String titulo);
}
