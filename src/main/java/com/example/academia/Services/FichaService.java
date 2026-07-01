package com.example.academia.Services;

import com.example.academia.Dtos.ExercicioRequest;
import com.example.academia.Dtos.ExercicioResponse;
import com.example.academia.Dtos.FichaRequest;
import com.example.academia.Dtos.FichaResponse;
import com.example.academia.Dtos.VideoResponse;
import com.example.academia.Entities.Exercicio;
import com.example.academia.Entities.Ficha;
import com.example.academia.Entities.Video;
import com.example.academia.Repositories.FichaRepository;
import com.example.academia.Repositories.VideoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FichaService {

    private final FichaRepository fichaRepository;
    private final VideoRepository videoRepository;

    @Transactional
    public FichaResponse criarFicha(FichaRequest request) {
        Ficha ficha = new Ficha();
        ficha.setTitulo(request.getTitulo());
        ficha.setDescricao(request.getDescricao());
        ficha.setCategoria(request.getCategoria());
        ficha.setData(LocalDate.now());

        if (request.getExercicios() != null) {
            for (ExercicioRequest exReq : request.getExercicios()) {
                Exercicio exercicio = criarExercicio(exReq, ficha);
                ficha.getExercicios().add(exercicio);
            }
        }

        Ficha salva = fichaRepository.save(ficha);
        return toResponse(salva);
    }

    public List<FichaResponse> listarTodas() {
        return fichaRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<FichaResponse> listarPorCategoria(String categoria) {
        return fichaRepository.findByCategoria(categoria).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<FichaResponse> buscarPorTitulo(String titulo) {
        return fichaRepository.findByTituloContainingIgnoreCase(titulo).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public FichaResponse buscarPorId(Long id) {
        Ficha ficha = fichaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ficha não encontrada"));
        return toResponse(ficha);
    }

    @Transactional
    public void deletarPorId(Long id) {
        fichaRepository.deleteById(id);
    }

    @Transactional
    public FichaResponse atualizarFicha(FichaRequest request, Long id) {
        Ficha ficha = fichaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ficha não encontrada"));

        ficha.setTitulo(request.getTitulo());
        ficha.setDescricao(request.getDescricao());
        ficha.setCategoria(request.getCategoria());

        ficha.getExercicios().clear();
        if (request.getExercicios() != null) {
            for (ExercicioRequest exReq : request.getExercicios()) {
                Exercicio exercicio = criarExercicio(exReq, ficha);
                ficha.getExercicios().add(exercicio);
            }
        }

        Ficha salva = fichaRepository.save(ficha);
        return toResponse(salva);
    }

    private Exercicio criarExercicio(ExercicioRequest exReq, Ficha ficha) {
        Exercicio exercicio = new Exercicio();
        exercicio.setNome(exReq.getNome());
        exercicio.setSeries(exReq.getSeries());
        exercicio.setRepeticoes(exReq.getRepeticoes());
        exercicio.setOrdem(exReq.getOrdem());
        exercicio.setFicha(ficha);

        if (exReq.getVideoId() != null) {
            Video video = videoRepository.findById(exReq.getVideoId())
                    .orElseThrow(() -> new RuntimeException("Vídeo não encontrado"));
            exercicio.setVideo(video);
        }

        return exercicio;
    }

    private FichaResponse toResponse(Ficha ficha) {
        List<ExercicioResponse> exercicios = ficha.getExercicios().stream()
                .map(ex -> {
                    VideoResponse videoResponse = null;
                    if (ex.getVideo() != null) {
                        Video v = ex.getVideo();
                        videoResponse = new VideoResponse(v.getId(), v.getNome(), v.getVideoUrl(), v.getCategoria());
                    }
                    return new ExercicioResponse(
                            ex.getId(),
                            ex.getNome(),
                            ex.getSeries(),
                            ex.getRepeticoes(),
                            ex.getOrdem(),
                            videoResponse
                    );
                })
                .collect(Collectors.toList());

        return new FichaResponse(
                ficha.getId(),
                ficha.getTitulo(),
                ficha.getDescricao(),
                ficha.getCategoria(),
                ficha.getData(),
                exercicios
        );
    }
}
