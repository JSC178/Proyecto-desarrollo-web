package com.barberia.service;

import com.barberia.domain.Usuario;
import com.barberia.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }


    public void guardarUsuario(Usuario usuario) {
        var encoder = new BCryptPasswordEncoder();
        
        
        if (usuario.getIdUsuario() != null && usuario.getIdUsuario() > 0) {
            Usuario existente = usuarioRepository.findById(usuario.getIdUsuario()).orElse(null);
            // Verificamos si es un usuario que ya existe (
            if (existente != null) {
                if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                    //si esta en blanco la contraseña, mantiene la vieja
                    usuario.setPassword(existente.getPassword());
                } else {
                    //si puso una nueva la encripta
                    usuario.setPassword(encoder.encode(usuario.getPassword()));
                }
                
                if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
                    usuario.setRol(existente.getRol());
                }
            }
        } else { 
            usuario.setPassword(encoder.encode(usuario.getPassword())); 
            
// si el usuario no existe, se encripta la clave y se le asigna un rol por defecto
            if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
                usuario.setRol("ROLE_USER"); 
            }
        }
        
        usuario.setActivo(true);
        usuarioRepository.save(usuario);
    }
    
    public Usuario getUsuarioPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Transactional
    public void actualizarPerfil(Usuario usuario) {
        Usuario actual = usuarioRepository.findById(usuario.getIdUsuario()).orElse(null);
        if (actual != null) {
            actual.setNombre(usuario.getNombre());
            actual.setApellidos(usuario.getApellidos());
            actual.setCorreo(usuario.getCorreo());
            actual.setTelefono(usuario.getTelefono());
            usuarioRepository.save(actual);
        }
    }

    // Eliminar Cuenta
    @Transactional
    public void eliminarUsuario(Long idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }
    
    @Transactional(readOnly = true)
    public Usuario getUsuarioPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario).orElse(null);
    }
}