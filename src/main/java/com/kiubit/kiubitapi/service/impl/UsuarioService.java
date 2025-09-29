package com.kiubit.kiubitapi.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kiubit.kiubitapi.model.ApiResponse;
import com.kiubit.kiubitapi.model.Usuario;
import com.kiubit.kiubitapi.repository.IUsuarioRepository;
import com.kiubit.kiubitapi.service.IUsuarioService;

@Service
public class UsuarioService implements IUsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Usuario crearUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUsername("admin");
        usuario.setPassword("$2a$10$7HfZKQXW.xQWvFZJzFZZkOXpUMdgrM2SAgfTeGEkIed6s6U5BefJm");
        usuario.setRole("ADMIN");
        return usuarioRepository.save(usuario);
    }

    @Override
    public ApiResponse<Usuario> registrarUsuario(Usuario usuario) {
        ApiResponse<Usuario> response = new ApiResponse<>();
        
        // Verificar si el usuario ya existe (por username o email)
        if (usuarioRepository.findByUsernameOrEmail(usuario.getUsername(), usuario.getEmail()).isPresent()) {
            response.setSuccess(false);
            response.setMessage("El usuario ya existe");
            response.setCode(400);
            response.setResult(null);
            return response;
        }
        
        // Hashear la contraseña antes de guardar
        String hashedPassword = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(hashedPassword);
        
        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        response.setSuccess(true);
        response.setMessage("Usuario creado");
        response.setCode(201);
        response.setResult(nuevoUsuario);
        return response;
    }

    @Override
    public ApiResponse<Usuario> actualizarUsuario(Usuario usuario) {
        ApiResponse<Usuario> response = new ApiResponse<>();
        
        // Verificar si el usuario existe
        if (!usuarioRepository.findById(usuario.getUid()).isPresent()) {
            response.setSuccess(false);
            response.setMessage("El usuario no existe");
            response.setCode(404);
            response.setResult(null);
            return response;
        }
        
        // Hashear la contraseña antes de guardar si fue modificada
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(hashedPassword);
        } else {
            // Mantener la contraseña actual si no se proporciona una nueva
            Usuario existingUser = usuarioRepository.findById(usuario.getUid()).get();
            usuario.setPassword(existingUser.getPassword());
        }

        Usuario updatedUser = usuarioRepository.save(usuario);
        response.setSuccess(true);
        response.setMessage("Usuario actualizado");
        response.setCode(200);
        response.setResult(updatedUser);
        return response;
    }
}