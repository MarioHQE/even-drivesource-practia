package com.example.curso.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.curso.Entity.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, String> {

}
