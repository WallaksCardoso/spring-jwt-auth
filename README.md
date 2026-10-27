# Spring JWT Auth

API REST de autenticação stateless com JWT: cadastro de usuários, login e proteção das demais rotas por token.

[![CI](https://github.com/WallaksCardoso/spring-jwt-auth/actions/workflows/ci.yml/badge.svg)](https://github.com/WallaksCardoso/spring-jwt-auth/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-21-informational)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-informational)
[![Licença](https://img.shields.io/badge/licen%C3%A7a-MIT-informational)](LICENSE)

## Sobre

Base de autenticação para APIs Spring: o usuário se cadastra, faz login e recebe um token JWT
assinado (HS256, validade de 24 horas). As demais rotas exigem esse token no cabeçalho
`Authorization`. Sem sessão no servidor: cada requisição é validada pelo token.

## Funcionalidades

- Cadastro de usuário com validação (nome de 3 a 20 caracteres, senha com no mínimo 6)
- Senha armazenada com hash (BCrypt)
- Login que devolve o token JWT
- Filtro que valida o token em todas as rotas protegidas
- Erros de validação devolvidos campo a campo
- Documentação interativa com Swagger (OpenAPI)
- Coleção do Postman em `src/main/resources/spring-jwt-auth.postman_collection.json`

## Stack

- Java 21 e Spring Boot 3.5 (Web, Security, Data JPA, Validation)
- JJWT 0.11
- PostgreSQL em produção; H2 em memória nos testes
- springdoc-openapi (Swagger UI)
- Maven, Docker e GitHub Actions

## Endpoints

| Método | Rota | Acesso | Descrição |
| --- | --- | --- | --- |
| `POST` | `/api/pessoas/cadastrar` | público | Cadastra um usuário |
| `POST` | `/api/auth/login` | público | Autentica e devolve o token |
| `GET` | `/actuator/health` | público | Saúde da aplicação |
| qualquer outra | | token | Exige `Authorization: Bearer <token>` |

Exemplo:

```bash
curl -X POST http://localhost:8080/api/pessoas/cadastrar \
  -H "Content-Type: application/json" \
  -d '{"nome": "maria", "senha": "segredo123"}'

curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"nome": "maria", "senha": "segredo123"}'
# {"token": "eyJhbGciOiJIUzI1NiJ9..."}
```

## Como rodar

Pré-requisitos: Java 21 e um PostgreSQL acessível.

A aplicação lê a configuração de variáveis de ambiente:

| Variável | Descrição |
| --- | --- |
| `SPRING_DATASOURCE_URL` | URL JDBC do PostgreSQL, ex.: `jdbc:postgresql://localhost:5432/auth` |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco |
| `JWT_SECRET` | Chave de assinatura do token, com pelo menos 32 caracteres |

```bash
./mvnw spring-boot:run
```

Com Docker:

```bash
docker build -t spring-jwt-auth .
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=... -e SPRING_DATASOURCE_USERNAME=... \
  -e SPRING_DATASOURCE_PASSWORD=... -e JWT_SECRET=... \
  spring-jwt-auth
```

A documentação da API fica em `http://localhost:8080/swagger-ui/index.html`.

## Testes

```bash
./mvnw verify
```

Os testes sobem a aplicação com H2 em memória (sem precisar de PostgreSQL) e cobrem o fluxo de
cadastro, login com token, senha errada, dados inválidos e acesso sem token. A CI roda a cada push.

## Estrutura

```
src/main/java/com/wallaks/spring_jwt_auth/
  config/       segurança (Spring Security) e CORS
  controller/   rotas de autenticação e cadastro
  dto/          entrada e saída da API
  exception/    tratamento global de erros
  model/        entidades JPA
  repository/   acesso ao banco
  security/     filtro que valida o JWT
  service/      regras de cadastro e usuários
  util/         geração e leitura do token
```

## Licença

[MIT](LICENSE)
