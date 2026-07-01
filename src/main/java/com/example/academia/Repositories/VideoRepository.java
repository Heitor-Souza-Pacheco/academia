package com.example.academia.Repositories;

import com.example.academia.Entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findByNomeContainingIgnoreCase(String nome);

    List<Video> findByCategoria(String categoria);
}
