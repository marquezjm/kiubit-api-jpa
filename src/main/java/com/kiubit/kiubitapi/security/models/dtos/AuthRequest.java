package com.kiubit.kiubitapi.security.models.dtos;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
