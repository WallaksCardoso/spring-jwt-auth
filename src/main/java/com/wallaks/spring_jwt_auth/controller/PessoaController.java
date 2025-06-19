package com.wallaks.spring_jwt_auth.controller;


import com.wallaks.spring_jwt_auth.dto.PessoaDTO;
import com.wallaks.spring_jwt_auth.model.Pessoa;
import com.wallaks.spring_jwt_auth.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @Operation(summary = "Cadastra uma nova pessoa no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pessoa cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/cadastrar")
    public ResponseEntity<Pessoa> cadastrarPessoa(@Valid @RequestBody PessoaDTO pessoaDTO) {
        Pessoa pessoa = pessoaService.cadastrarPessoa(pessoaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
    }
}
