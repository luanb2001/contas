# Contas API

API REST para gestão de contas a pagar, desenvolvida em Java 17 com Spring Boot 3.

---

## Stack

- **Java 17** + **Spring Boot 3.3**
- **PostgreSQL** (persistência) + **Flyway** (migrações)
- **Spring Data JPA** (ORM)
- **RabbitMQ** (importação assíncrona de CSV)
- **Spring Security + JWT** (autenticação stateless)
- **Swagger / OpenAPI** (documentação interativa)
- **Docker + Docker Compose** (infraestrutura)

---

## Como executar

### Pré-requisitos
- Docker e Docker Compose instalados

### Subindo todos os serviços

```bash
docker-compose up --build
```

Isso sobe três serviços:
- **app** – API na porta `8080`
- **postgres** – PostgreSQL na porta `5435`
- **rabbitmq** – RabbitMQ (AMQP `5672`, Management UI `15672`)

A aplicação aguarda PostgreSQL e RabbitMQ estarem saudáveis antes de iniciar (healthchecks configurados no compose).

### Swagger UI

Após subir, acesse: http://localhost:8080/swagger-ui.html

---

## Autenticação (JWT)

Todas as rotas (exceto `/auth/login` e Swagger) exigem um Bearer Token.

### 1. Obter token

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "desafio", "password": "contas"}'
```

**Resposta:**
```json
{ "token": "<jwt-token>" }
```

### 2. Usar o token nas demais chamadas

Adicione o header em todas as requisições:
```
Authorization: Bearer <jwt-token>
```

---

## Endpoints

### Fornecedor

#### Cadastrar fornecedor
```bash
curl -X POST http://localhost:8080/fornecedor \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"nome": "Fornecedor XPTO"}'
```

#### Buscar fornecedor por ID
```bash
curl http://localhost:8080/fornecedor/{id} \
  -H "Authorization: Bearer <token>"
```

---

### Contas

#### Cadastrar conta
```bash
curl -X POST http://localhost:8080/conta \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "dataVencimento": "2025-12-31T00:00:00",
    "dataPagamento": null,
    "descricao": "Conta de luz",
    "situacaoContaEnum": "ABERTA",
    "valor": 175.50,
    "fornecedorId": "<uuid-do-fornecedor>"
  }'
```

#### Atualizar conta
```bash
curl -X PUT http://localhost:8080/conta \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "<uuid-da-conta>",
    "dataVencimento": "2025-12-31T00:00:00",
    "dataPagamento": "2025-11-01T10:00:00",
    "descricao": "Conta de luz atualizada",
    "situacaoContaEnum": "PAGA",
    "valor": 175.50,
    "fornecedorId": "<uuid-do-fornecedor>"
  }'
```

#### Atualizar situação da conta
```bash
curl -X PATCH http://localhost:8080/conta \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"id": "<uuid>", "situacaoContaEnum": "CANCELADA"}'
```

Situações disponíveis: `ABERTA`, `VENCIDA`, `CANCELADA`, `PAGA`

#### Buscar conta por ID
```bash
curl http://localhost:8080/conta/{id} \
  -H "Authorization: Bearer <token>"
```

#### Listar contas (paginado com filtros)
```bash
curl "http://localhost:8080/conta/listar-contas?data-vencimento-inicial=2024-01-01T00:00:00&data-vencimento-final=2025-12-31T23:59:59&descricao=luz&pagina=0&maximo-por-pagina=10" \
  -H "Authorization: Bearer <token>"
```

#### Valor total pago por período
```bash
curl "http://localhost:8080/conta/carregar-valor-pago?data-inicial=2024-01-01T00:00:00&data-final=2024-12-31T23:59:59" \
  -H "Authorization: Bearer <token>"
```

#### Importar contas via CSV (assíncrono)
```bash
curl -X POST http://localhost:8080/conta/importar-conta \
  -H "Authorization: Bearer <token>" \
  -F "file=@src/main/resources/gerar-contas.csv"
```

Retorna um `UUID` de protocolo. Use-o para acompanhar o status da importação:

```bash
curl http://localhost:8080/importacao/{protocolo-id} \
  -H "Authorization: Bearer <token>"
```

**Formato do CSV:**
```csv
dataVencimento,dataPagamento,descricao,situacaoContaEnum,valor,fornecedorId
2024-09-20 12:00:00,,Conta de luz,ABERTA,175.50,00000000-0000-0000-0000-000000000001
```

Status de importação: `PENDENTE`, `PROCESSANDO`, `FINALIZADA`, `FINALIZADA_COM_ERROS`, `ERRO`

---

## Decisões Arquiteturais

### Arquitetura Hexagonal (Ports & Adapters)
O projeto segue arquitetura hexagonal com separação clara entre camadas:
- **`domain`** – Entidades (`Conta`, `Fornecedor`, `ImportacaoConta`), interfaces de repositório e regras de negócio intrínsecas ao domínio.
- **`application`** – Use cases (interfaces/ports) e services (implementações). Orquestram o fluxo sem depender de detalhes de infraestrutura.
- **`adapters/in`** – Controllers REST e listeners RabbitMQ (entradas do sistema).
- **`adapters/out`** – Repositórios JPA e producers RabbitMQ (saídas do sistema).
- **`infrastructure`** – Configurações Spring (Security, RabbitMQ, OpenAPI).

### Prevenção de N+1 (Performance JPA)
Queries que envolvem `Conta` e `Fornecedor` usam **projeção em DTO via construtor JPQL**, carregando apenas os campos necessários em uma única query. O relacionamento `Conta → Fornecedor` utiliza `FetchType.LAZY`, evitando carregamento desnecessário.

### Importação Assíncrona (CSV)
O endpoint de importação recebe o arquivo, cria um registro `ImportacaoConta` com status `PENDENTE`, serializa o conteúdo em Base64 e publica na fila `importacao-conta-queue`. O consumer processa cada linha individualmente em background, atualizando o status para `FINALIZADA` ou `FINALIZADA_COM_ERROS`. Falhas graves são encaminhadas à Dead Letter Queue (`importacao-conta-dlq`) para reprocessamento manual.

### Invariantes de Domínio
Regras intrínsecas são aplicadas nos próprios métodos de domínio:
- Uma conta `PAGA` ou `CANCELADA` não pode ter seu estado revertido (`pagar()`, `cancelar()` lançam `RegraNegocioException`).
- `valor` negativo ou zero é rejeitado via `@Positive` (Bean Validation) antes de chegar ao domínio.

### Segurança (JWT Stateless)
Spring Security configurado como STATELESS. O `JwtAuthenticationFilter` intercepta cada request, valida o Bearer Token via `JwtService` (JJWT 0.12.x) e popula o `SecurityContext`. Rotas públicas: `/auth/login`, `/swagger-ui/**`, `/v3/api-docs/**`.

### Flyway
Migrações versionadas em `src/main/resources/db/migration/`:
- `V1` – Criação da tabela `conta`
- `V2` – Criação da tabela `fornecedor`
- `V3` – Adição da FK `fornecedor_id` em `conta`
- `V4` – Criação da tabela `importacao_conta`
- `V5` – Migração da coluna `situacao` de SMALLINT para VARCHAR

---

## LinkedIn
https://www.linkedin.com/in/luan-barbosa-meneghelli-773b5920a/
