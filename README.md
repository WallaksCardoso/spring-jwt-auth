# Spring JWT Auth

Este projeto é uma API REST desenvolvida com Java 21 e Spring Boot 3.5.0 que implementa autenticação de usuários baseada em JWT (JSON Web Token). Ele permite o cadastro de usuários, login e acesso a endpoints protegidos.

---

##  Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.0**
- **Spring Security**
- **Spring Data JPA**
- **JWT (JSON Web Token)**
- **H2 Database** (em memória)
- **Spring Boot Starter Validation**
- **Swagger (OpenAPI)**
- **Maven**

---

## ️ Configuração do Ambiente

### Pré-requisitos

- Java 21 instalado
- Maven instalado

---

## ️ Banco de Dados

Este projeto utiliza **H2 Database** (em memória) para facilitar testes locais.  
O console pode ser acessado em:

http://localhost:8080/h2-console

---

## Build e Execução

Para compilar o projeto:

    mvn clean install

Para executar localmente:

    mvn spring-boot:run

---

##  Swagger - Documentação da API

Acesse via navegador:

    http://localhost:8080/swagger-ui/index.html

---

##  Endpoints Principais

###  Autenticação

- `POST /auth/login` – Realiza login e retorna o token JWT  
  **Requisição:**

  {
  "nome": "usuario123",
  "senha": "123456"
  }

###  Usuários

- `POST /api/pessoas/cadastrar` – Cadastra novo usuário  
  **Requisição:**

  {
  "nome": "usuario123",
  "senha": "123456"
  }

---

> ⚠️ Todos os outros endpoints protegidos exigem o token JWT no header:

    Authorization: Bearer <token>
