package com.example.academia.Services;

import com.example.academia.Entities.Ficha;
import com.example.academia.Repositories.FichaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FichaService {

    @Autowired
    private final FichaRepository fichaRepository;

    public FichaService(FichaRepository fichaRepository) {
        this.fichaRepository = fichaRepository;
    }

    //admin
    @Transactional
    public Ficha criarFicha(Ficha ficha){
        ficha.setData(LocalDate.now());
        ficha.getExercicios().forEach(ex -> ex.setFicha(ficha));
        return fichaRepository.save(ficha);
    }

    public List<Ficha> listarTodas(){
        return fichaRepository.findAll();
    }

    public List<Ficha> listarPorCategoria(String categoria){
        return fichaRepository.findByCategoria(categoria);
    }

    public List<Ficha> buscarPorTitulo(String titulo){
        return fichaRepository.findByTituloContaingIgnoreCase(titulo);
    }

    public Ficha buscarPorId(Long id){
        return fichaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ficha não encontrada"));

    }

    //admin
    @Transactional
    public void deletarPorId(Long id){
        fichaRepository.deleteById(id);
    }

    //admin
    public Ficha atualizarFicha(Ficha fichaNova, Long id){
        Ficha fichaVelha = buscarPorId(id);

        fichaVelha.setTitulo(fichaNova.getTitulo());
        fichaVelha.setDescricao(fichaNova.getDescricao());
        fichaVelha.setCategoria(fichaNova.getCategoria());
        fichaVelha.setData(fichaNova.getData());

        fichaVelha.getExercicios().clear();
        fichaNova.getExercicios().forEach(ex -> {
            ex.setFicha(fichaVelha);
            fichaVelha.getExercicios().add(ex);
        });

        return fichaRepository.save(fichaVelha);
    }
}
