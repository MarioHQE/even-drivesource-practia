package com.example.curso.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.curso.Entity.Curso;
import com.example.curso.Repository.CursoRepository;

@Service
public class CursoService {
    @Autowired
    WebClient.Builder webClientBuilder;
    private final String USUARIO_SERVICE_URL = "http://localhost:8081/usuario";
    @Autowired
    CursoRepository cursoRepository;

    public Page<Curso> findAllCurso(Pageable pageable) {
        return cursoRepository.findAll(pageable);

    }

    public ResponseEntity<Curso> agregarCurso(Curso curso) {

        return ResponseEntity.ok(cursoRepository.save(curso));
    }

    public ResponseEntity<Curso> actualizarCurso(Curso curso, String id) {
        Curso cursoExistente = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + id));
        cursoExistente.setTitulo(curso.getTitulo());
        cursoExistente.setDescripcion(curso.getDescripcion());
        cursoExistente.setDuracion(curso.getDuracion());
        cursoExistente.setPrecio(curso.getPrecio());

        return ResponseEntity.ok(cursoRepository.save(cursoExistente));
    }

    public ResponseEntity<Void> eliminarCurso(String id) {
        Curso cursoExistente = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + id));
        cursoRepository.delete(cursoExistente);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<String> asignarcurso(String idusuario, String idcurso) {
        try {
            Boolean usurioExiste = webClientBuilder.build().get()
                    .uri(USUARIO_SERVICE_URL + "/verificar/" + idusuario)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .doOnError(err -> System.err.println("Error: " + err))
                    .block();
            if (usurioExiste == null || !usurioExiste) {
                return ResponseEntity.badRequest().body("El usuario no existe");
            } else {
                Curso curso = cursoRepository.findById(idcurso)
                        .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + idcurso));

                cursoRepository.save(curso);
                return ResponseEntity.ok("Curso asignado al usuario correctamente");

            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al asignar el curso: " + e.getMessage());
        }

    }
}
