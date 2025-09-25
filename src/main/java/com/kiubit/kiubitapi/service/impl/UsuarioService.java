package com.kiubit.kiubitapi.service.impl;

import org.springframework.stereotype.Service;

import com.kiubit.kiubitapi.model.Usuario;
import com.kiubit.kiubitapi.repository.IUsuarioRepository;
import com.kiubit.kiubitapi.service.IUsuarioService;

@Service
public class UsuarioService implements IUsuarioService {

    private final IUsuarioRepository usuarioRepository;

    public UsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario crearUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUsername("admin");
        usuario.setPassword("$2a$10$7HfZKQXW.xQWvFZJzFZZkOXpUMdgrM2SAgfTeGEkIed6s6U5BefJm");
        usuario.setRole("ADMIN");
        return usuarioRepository.save(usuario);
    }

}
