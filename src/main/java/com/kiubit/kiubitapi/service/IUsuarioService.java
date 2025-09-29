package com.kiubit.kiubitapi.service;

import com.kiubit.kiubitapi.model.ApiResponse;
import com.kiubit.kiubitapi.model.Usuario;

public interface IUsuarioService {
    Usuario crearUsuario();
    ApiResponse<Usuario> registrarUsuario(Usuario usuario);
    ApiResponse<Usuario> actualizarUsuario(Usuario usuario);
}
