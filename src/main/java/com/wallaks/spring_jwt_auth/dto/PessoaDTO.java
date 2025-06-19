package com.wallaks.spring_jwt_auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PessoaDTO {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 20, message = "O nome deve ter entre 3 e 20 caracteres.")
    private String nome;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    private String senha;
}
