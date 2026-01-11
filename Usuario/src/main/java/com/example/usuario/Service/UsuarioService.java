package com.example.usuario.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.usuario.Repository.UsuarioRepository;
import com.example.usuario.Entity.Usuario;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public boolean verificarUsuario(String idusuario) {

        return usuarioRepository.existsById(idusuario);
    }

    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(String id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> updateUsuario(String id, Usuario data) {
        return usuarioRepository.findById(id).map(u -> {
            if (data.getNombre() != null)
                u.setNombre(data.getNombre());
            if (data.getEmail() != null)
                u.setEmail(data.getEmail());
            if (data.getTelefono() != null)
                u.setTelefono(data.getTelefono());
            if (data.getActivo() != null)
                u.setActivo(data.getActivo());
            return usuarioRepository.save(u);
        });
    }

    public boolean deleteUsuario(String id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
