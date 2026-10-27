package com.wallaks.spring_jwt_auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AutenticacaoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private void cadastrar(String nome, String senha) throws Exception {
        mockMvc.perform(post("/api/pessoas/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"" + nome + "\",\"senha\":\"" + senha + "\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroELoginDevolvemToken() throws Exception {
        cadastrar("maria", "segredo123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"maria\",\"senha\":\"segredo123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void loginComSenhaErradaERecusado() throws Exception {
        cadastrar("joao", "segredo123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"joao\",\"senha\":\"outrasenha\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void cadastroComDadosInvalidosERecusado() throws Exception {
        mockMvc.perform(post("/api/pessoas/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"ab\",\"senha\":\"123\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nome").exists())
                .andExpect(jsonPath("$.senha").exists());
    }

    @Test
    void rotaProtegidaSemTokenERecusada() throws Exception {
        mockMvc.perform(get("/api/pessoas"))
                .andExpect(status().isForbidden());
    }
}
