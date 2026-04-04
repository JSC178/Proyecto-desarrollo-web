/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.barberia.service.impl;


import com.barberia.domain.Usuario;
import com.barberia.repository.UsuarioRepository;
import com.barberia.service.UsuarioDetailsService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User; // ¡IMPORTANTE!
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("userDetailsService")
public class UsuarioDetailsServiceImpl implements UsuarioDetailsService, UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscamos el usuario en la base de datos
        Usuario usuario = usuarioRepository.findByUsername(username);
        
        if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }
        
        // Retornamos un objeto de seguridad de Spring con el usuario y sus roles
        // Nota: Persona 1 debe ayudarte con la tabla de roles según Lección 10
        return new User(usuario.getUsername(), usuario.getPassword(), new ArrayList<>());
    }
}
