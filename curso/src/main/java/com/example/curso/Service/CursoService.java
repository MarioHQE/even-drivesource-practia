package com.example.curso.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.curso.Entity.Curso;
import com.example.curso.Entity.ListadeUsuario;
import com.example.curso.Repository.CursoRepository;
import com.example.curso.Repository.ListadeUsuarioRepository;
import com.example.curso.client.UsuarioClient;

@Service
public class CursoService {
    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ListadeUsuarioRepository listadeUsuarioRepository;

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
            Boolean usuarioExiste = usuarioClient.existuser(idusuario);
            if (usuarioExiste == null || !usuarioExiste) {
                return ResponseEntity.badRequest().body("El usuario no existe");
            }

            Curso curso = cursoRepository.findById(idcurso)
                    .orElse(null);

            if (curso == null) {
                return ResponseEntity.badRequest().body("El curso no existe");
            }

            // 3. Crear la asignación
            ListadeUsuario asignacion = new ListadeUsuario();
            asignacion.setCurso(curso);
            asignacion.setIdUsuario(idusuario);
            listadeUsuarioRepository.save(asignacion);

            return ResponseEntity.ok("Curso asignado al usuario correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Error al asignar el curso: " + e.getMessage());
        }
    }
}
