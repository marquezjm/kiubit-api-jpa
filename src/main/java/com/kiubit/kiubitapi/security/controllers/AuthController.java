package com.kiubit.kiubitapi.security.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiubit.kiubitapi.model.ApiResponse;
import com.kiubit.kiubitapi.model.Usuario;
import com.kiubit.kiubitapi.security.models.dtos.AuthRequest;
import com.kiubit.kiubitapi.security.models.dtos.AuthResponse;
import com.kiubit.kiubitapi.security.services.UsuarioDetailsService;
import com.kiubit.kiubitapi.service.IUsuarioService;
import com.kiubit.kiubitapi.utils.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UsuarioDetailsService uds;
    private final IUsuarioService usuarioService;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil, UsuarioDetailsService uds, IUsuarioService usuarioService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.uds = uds;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody AuthRequest body) {
        ApiResponse<AuthResponse> response = new ApiResponse<>();
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword()));

        UserDetails ud = (UserDetails) auth.getPrincipal();
        String token = jwtUtil.generateToken(ud);

        response.setSuccess(true);
        response.setMessage("Login exitoso");
        response.setCode(200);
        response.setResult(new AuthResponse(token));

        return response;
    }

    @PostMapping("register")
    public ApiResponse<Usuario> registrar(@RequestBody Usuario usuario) {
        return usuarioService.registrarUsuario(usuario);
    }

    @PostMapping("update")
    public ApiResponse<Usuario> actualizar(@RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(usuario);
    }
    

    @GetMapping("crear")
    public ApiResponse<Usuario> crearUsuario() {
        ApiResponse<Usuario> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage("Usuario creado");
        response.setCode(201);
        response.setResult(usuarioService.crearUsuario());
        return response;
    }
    
}