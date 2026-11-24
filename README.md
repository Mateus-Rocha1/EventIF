# EventIF — Sistema de Gestão de Eventos Acadêmicos

O **EventIF** é um sistema web desenvolvido para divulgar eventos acadêmicos (semanas de curso, congressos, jornadas), receber inscrições em atividades, controlar presença e emitir certificados.

O sistema atende a três públicos autenticados e visitantes:
- **Visitante (sem login):** visualiza eventos publicados e cria conta de participante.
- **Participante (`ROLE_PARTICIPANTE`):** inscreve-se em atividades, cancela inscrição e visualiza/emite certificados.
- **Organizador (`ROLE_ORGANIZADOR`):** cria e gerencia seus próprios eventos e atividades e registra a presença dos participantes.
- **Administrador (`ROLE_ADMIN`):** gerencia categorias de eventos e define quem é organizador.

> 📄 Para detalhes técnicos sobre o modelo de dados, camadas e segurança, consulte a [Documentação de Arquitetura](docs/arquitetura.md).

---

## 👥 Integrantes da Equipe

- **Mateus Rocha** — [GitHub](https://github.com/Mateus-Rocha1)
- **Camila Santiago** — [GitHub](https://github.com/Camilay3)

---

## 🎥 Vídeo de Apresentação

- **Link do vídeo:** `[INSERIR LINK DO VÍDEO AQUI]`

---

## ⚙️ Pré-requisitos

- **Docker** (24.0+) e **Docker Compose** (v2+)
- *(Tecnologias contidas nos containers: Java 21, Maven 3.9 e PostgreSQL 16 — não é necessário instalá-los na máquina local).*

---

## 🚀 Passo a Passo para Subir o Projeto

1. Clone o repositório e acesse a pasta do projeto:
   ```bash
   git clone git@github.com:Mateus-Rocha1/EventIF.git
   cd EventIF
   ```
2. Crie o arquivo `.env` a partir do modelo `.env.example`:
   ```bash
   cp .env.example .env
   ```
   E preencha as variáveis de ambiente no `.env` (exemplo para ambiente local/Docker):
   ```env
   #PROFILE

   SPRING_PROFILES_ACTIVE=dev

   #DATABASE

   POSTGRES_DB=eventif
   POSTGRES_USER=eventif
   POSTGRES_PASSWORD=eventif

   #JWT

   JWT_SECRET=eventif-secret-key-dev-2026
   ```
3. Suba o banco de dados e a aplicação com:
   ```bash
   docker compose up --build
   ```

### 🌐 URLs de Acesso

| Serviço | Descrição | URL |
| :--- | :--- | :--- |
| **Aplicação Web** | Interface web renderizada no servidor (Thymeleaf) | [http://localhost:8080](http://localhost:8080) |
| **Swagger UI** | Documentação interativa dos endpoints (OpenAPI) | [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) |

---

## 🧪 Usuários de Teste (Ambiente de Desenvolvimento)

> ⚠️ **Nota:** Os usuários abaixo são criados automaticamente pela migration repetível (`src/main/resources/db/dev/R__INSERT_USUARIOS_TESTE.sql`), que só é executada quando o perfil `SPRING_PROFILES_ACTIVE=dev` está ativo (`application-dev.properties`). Em produção (`SPRING_PROFILES_ACTIVE=prod`), apenas `classpath:db/migration` é carregado.

| Perfil | Role | E-mail | Senha |
| :--- | :--- | :--- | :--- |
| **Administrador** | `ROLE_ADMIN` | `admin@eventif.com` | `admin123` |
| **Organizador** | `ROLE_ORGANIZADOR` | `organizador@eventif.com` | `org123` |
| **Participante** | `ROLE_PARTICIPANTE` | `participante@eventif.com` | `part123` |

---

## 📂 Estrutura do Projeto

```text
eventif/
├── docs/
│   └── arquitetura.md                 # Documentação arquitetural e diagrama ER
├── src/
│   ├── main/
│   │   ├── java/com/eventif/
│   │   │   ├── configuracao/security/ # Configuração do Spring Security 6 e filtro JWT
│   │   │   ├── controller/            # Controllers web e endpoints de autenticação/perfis
│   │   │   ├── dto/                   # Records de requisição e resposta com Bean Validation
│   │   │   ├── exception/             # Handler global de exceções e exceções de negócio
│   │   │   ├── model/                 # 6 Entidades JPA obrigatórias + RefreshToken + Enums
│   │   │   ├── repository/            # Interfaces Spring Data JPA
│   │   │   └── service/               # Regras de negócio, JWT, Cookies e Refresh Token
│   │   └── resources/
│   │       ├── db/
│   │       │   ├── migration/             # Scripts SQL de schema e perfis (V1 a V8 — dev e prod)
│   │       │   └── dev/                   # Seed repetível de usuários de teste (R__ — apenas dev)
│   │       ├── static/                    # Arquivos estáticos (CSS, JS, imagens)
│   │       ├── templates/                 # Templates HTML renderizados no servidor (Thymeleaf)
│   │       ├── application.properties     # Configurações comuns do Spring Boot e JPA
│   │       ├── application-dev.properties # Configurações exclusivas de desenvolvimento
│   │       └── application-prod.properties# Configurações exclusivas de produção
│   └── test/                          # Testes automatizados (com H2 em escopo de teste)
├── .dockerignore
├── .env.example
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

---

## ✨ Extras Implementados e Status

### Extras Implementados
- **Containerização completa:** `Dockerfile` e `docker-compose.yml` com *healthcheck* no PostgreSQL e volume persistente.
- **Versionamento de banco com Flyway:** migrations separadas por entidade (`V1` a `V8`) com validação estrita do schema (`ddl-auto=validate`).
- **Autenticação com JWT + Cookies `HttpOnly` + Rotação de Refresh Token:** proteção contra leitura de token via JavaScript (XSS) e renovação transparente de sessão.
- **Documentação interativa com Swagger UI (Springdoc OpenAPI).**
- **Auditoria JPA (`@EnableJpaAuditing`):** rastreamento automático de `criadoEm` e `atualizadoEm`.

### O que ficou faltando / Próximas Etapas
- `[PREENCHER SE ALGUMA FUNCIONALIDADE FICAR PENDENTE NA ENTREGA FINAL OU REMOVER SE TUDO FOR CONCLUÍDO]`
