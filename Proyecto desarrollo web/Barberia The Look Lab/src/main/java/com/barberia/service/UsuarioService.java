package com.barberia.service;

import com.barberia.domain.Usuario;
import com.barberia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void guardarUsuario(Usuario usuario) {
        var encoder = new BCryptPasswordEncoder();
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        
        usuario.setActivo(true);
        
        usuarioRepository.save(usuario);
    }
    
    public Usuario getUsuarioPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
    // Editar Perfi: Actualiza sin tocar la contraseña
    @Transactional
    public void actualizarPerfil(Usuario usuario) {
        Usuario actual = usuarioRepository.findById(usuario.getIdUsuario()).orElse(null);
        if (actual != null) {
            actual.setNombre(usuario.getNombre());
            actual.setApellidos(usuario.getApellidos());
            actual.setCorreo(usuario.getCorreo());
            actual.setTelefono(usuario.getTelefono());
            // Guardamos los cambios en la base de datos local
            usuarioRepository.save(actual);
        }
    }

    // Eliminar Cuenta
    @Transactional
    public void eliminarUsuario(Long idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }
    
    // Para cargar los datos del usuario en el formulario de edición
    @Transactional(readOnly = true)
    public Usuario getUsuarioPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario).orElse(null);
    }
}