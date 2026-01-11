package com.example.usuario.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.usuario.Entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

}
