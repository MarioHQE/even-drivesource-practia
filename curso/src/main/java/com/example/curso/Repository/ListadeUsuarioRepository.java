package com.example.curso.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.curso.Entity.ListadeUsuario;

@Repository
public interface ListadeUsuarioRepository extends JpaRepository<ListadeUsuario, String> {

}
