package com.kiubit.kiubitapi.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kiubit.kiubitapi.model.Usuario;
import com.kiubit.kiubitapi.repository.IUsuarioRepository;
import com.kiubit.kiubitapi.security.models.UsuarioDetails;

@Service
public class UsuarioDetailsService implements UserDetailsService {
    private final IUsuarioRepository repo;

    public UsuarioDetailsService(IUsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No existe: " + username));
        return new UsuarioDetails(usuario);
    }
}