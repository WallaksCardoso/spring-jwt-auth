package com.wallaks.spring_jwt_auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    @Schema(description = "Token JWT gerado após autenticação")
    private String token;
}
