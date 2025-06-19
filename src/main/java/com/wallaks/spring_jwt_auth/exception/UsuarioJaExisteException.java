package com.wallaks.spring_jwt_auth.exception;

public class UsuarioJaExisteException extends RuntimeException {
    public UsuarioJaExisteException(String nome) {
        super("Já existe um usuário com o nome: " + nome);
    }
}