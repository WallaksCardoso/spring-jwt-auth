# spring-jwt-auth

[![CI](https://github.com/WallaksCardoso/spring-jwt-auth/actions/workflows/ci.yml/badge.svg)](https://github.com/WallaksCardoso/spring-jwt-auth/actions/workflows/ci.yml)

Serviço de autenticação do sistema da pelada. O usuário se cadastra, faz login e recebe um JWT; o
[pelada-gestao](https://github.com/WallaksCardoso/pelada-gestao) valida esse token com a mesma
chave, sem precisar consultar este serviço a cada requisição.

Separei a autenticação em um serviço próprio para estudar como fica o login quando há mais de uma
API: nenhuma sessão no servidor, só o token assinado (HS256, 24 horas por padrão).

## Rotas

| Método | Rota | Acesso |
| --- | --- | --- |
| `POST` | `/api/pessoas/cadastrar` | público |
| `POST` | `/api/auth/login` | público |
| `GET` | `/actuator/health` | público |
| demais | | `Authorization: Bearer <token>` |

```bash
curl -X POST http://localhost:8081/api/pessoas/cadastrar \
  -H "Content-Type: application/json" \
  -d '{"nome": "maria", "senha": "segredo123"}'

curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"nome": "maria", "senha": "segredo123"}'
```

Nome com 3 a 20 caracteres e senha com pelo menos 6. Nome repetido devolve 409, a senha é guardada
com BCrypt e nunca volta na resposta. O Swagger fica em `/swagger-ui/index.html` e há uma coleção do
Postman em `src/main/resources`.

## Rodando

O jeito mais simples é subir o sistema inteiro pelo Docker Compose do
[pelada-gestao](https://github.com/WallaksCardoso/pelada-gestao#subindo-tudo).

Sozinho, com Java 21 e um PostgreSQL:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/auth
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=postgres
export JWT_SECRET=uma-chave-com-pelo-menos-32-caracteres
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

O perfil `local` cria as tabelas e sobe na porta 8081. Opcionais: `JWT_EXPIRACAOMS` (validade do
token) e `CORS_ORIGENS` (origens liberadas, separadas por vírgula).

## Testes

```bash
./mvnw verify
```

Sobem a aplicação com H2 em memória e passam pelo fluxo completo: cadastro, nome repetido, login,
senha errada, dados inválidos e acesso sem token.

## Licença

MIT
