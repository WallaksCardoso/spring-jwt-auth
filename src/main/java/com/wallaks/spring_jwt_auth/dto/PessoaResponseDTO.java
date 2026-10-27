package com.wallaks.spring_jwt_auth.dto;

import com.wallaks.spring_jwt_auth.model.Pessoa;

public record PessoaResponseDTO(Long id, String nome) {

    public static PessoaResponseDTO de(Pessoa pessoa) {
        return new PessoaResponseDTO(pessoa.getId(), pessoa.getNome());
    }
}
