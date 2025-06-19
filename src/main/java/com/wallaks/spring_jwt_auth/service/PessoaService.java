package com.wallaks.spring_jwt_auth.service;

import com.wallaks.spring_jwt_auth.dto.PessoaDTO;
import com.wallaks.spring_jwt_auth.exception.UsuarioJaExisteException;
import com.wallaks.spring_jwt_auth.model.Pessoa;
import com.wallaks.spring_jwt_auth.model.Usuario;
import com.wallaks.spring_jwt_auth.repository.PessoaRepository;
import com.wallaks.spring_jwt_auth.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository,
                         UsuarioRepository usuarioRepository,
                         BCryptPasswordEncoder passwordEncoder) {
        this.pessoaRepository = pessoaRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Pessoa cadastrarPessoa(PessoaDTO pessoaDTO) {

        if (usuarioRepository.findByNome(pessoaDTO.getNome()).isPresent()) {
            throw new UsuarioJaExisteException(pessoaDTO.getNome());
        }

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaDTO.getNome());
        pessoa = pessoaRepository.save(pessoa);

        Usuario usuario = new Usuario();
        usuario.setNome(pessoaDTO.getNome());
        usuario.setSenha(passwordEncoder.encode(pessoaDTO.getSenha()));
        usuarioRepository.save(usuario);

        return pessoa;
    }

}
