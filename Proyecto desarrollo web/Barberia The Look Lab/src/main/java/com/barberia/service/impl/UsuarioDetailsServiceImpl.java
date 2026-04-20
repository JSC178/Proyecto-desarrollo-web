package com.barberia.service.impl;

import com.barberia.domain.Usuario;
import com.barberia.repository.UsuarioRepository;
import com.barberia.service.UsuarioDetailsService;
import java.util.ArrayList;
import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority; 
import org.springframework.security.core.authority.SimpleGrantedAuthority; 
import org.springframework.security.core.userdetails.User; 
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
    Usuario usuario = usuarioRepository.findByUsername(username); //toma el usuario y lo busca en BD
    
    if (usuario == null) {
        System.out.println(" Error: Usuario no encontrado en BD: " + username);
        throw new UsernameNotFoundException(username);
    }

    List<GrantedAuthority> roles = new ArrayList<>();
    
    roles.add(new SimpleGrantedAuthority(usuario.getRol())); //guarda el rol de la bd y lo convierte en una autoridad valid
    
    System.out.println(" LOGIN EXITOSO - Usuario: " + username + " - Rol en BD: " + usuario.getRol());
    
    return new User(usuario.getUsername(), usuario.getPassword(), roles);
}
}