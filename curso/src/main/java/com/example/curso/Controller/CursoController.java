package com.example.curso.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.curso.Entity.Curso;
import com.example.curso.Service.CursoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class CursoController {
    @Autowired
    CursoService cursoService;

    @GetMapping("/findcursos")
    public Page<Curso> findCursos(Pageable pageable) {
        return cursoService.findAllCurso(pageable);
    }

    @PostMapping("/agregar")
    public ResponseEntity<Curso> AgregarCurso(@RequestBody Curso curso) {
        return cursoService.agregarCurso(curso);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Curso> putMethodName(@PathVariable String id, @RequestBody Curso curso) {

        return cursoService.actualizarCurso(curso, id);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable String id) {
        return cursoService.eliminarCurso(id);
    }

    @PostMapping("/asignarcurso")
    public ResponseEntity<String> asignarcurso(@RequestParam String idusuario, @RequestParam String idcurso) {

        return cursoService.asignarcurso(idusuario, idcurso);
    }

}
