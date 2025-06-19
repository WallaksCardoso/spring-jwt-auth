package com.wallaks.spring_jwt_auth.repository;


import com.wallaks.spring_jwt_auth.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
