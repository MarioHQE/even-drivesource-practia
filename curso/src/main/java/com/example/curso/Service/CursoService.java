package com.example.curso.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.curso.Entity.Curso;
import com.example.curso.Repository.CursoRepository;

@Service
public class CursoService {
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

    public String asignarcurso(String idusuario, String idcurso) {
        return "Curso asignado correctamente";
    }
}
