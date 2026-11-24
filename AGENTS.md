# AGENTS.md — Guia para Agentes de IA (EventIF)

Este arquivo define as regras de comportamento, convenções de código, fluxo de Git e restrições de stack que todo agente de IA deve seguir no projeto **EventIF**.

---

## 1. Regras Gerais de Comportamento e Git

- **NUNCA faça `git push` ou altere o histórico do Git sem autorização explícita do usuário.**
- **Idioma do Domínio:** Todo o código de domínio (entidades, atributos, métodos de serviço, DTOs, tabelas, colunas e mensagens de erro) deve estar em **Português** (`Usuario`, `Perfil`, `Categoria`, `Evento`, `Atividade`, `Inscricao`). Apenas termos técnicos universais permanecem em inglês (`RefreshToken`, `login`, `logout`, `refresh`, `TokenJWT`, `Cookie`, `Dto`, `Controller`, `Service`, `Repository`).
- **Sem Lombok:** Não utilize Lombok (`@Getter`, `@Setter`, `@Data`, etc.). Escreva construtores, getters e setters explicitamente nas entidades e classes Java. Use `record` para DTOs.
- **Arquitetura em Camadas:** Mantenha a separação `controller` $\rightarrow$ `service` $\rightarrow$ `repository`. Toda regra de negócio deve ficar exclusivamente na camada `service`.
- **Conventional Commits (em português):** `feat:`, `fix:`, `refactor:`, `chore:`, `docs:`, `test:`.

---

## 2. Stack e Restrições Tecnológicas

- **Java 21** e **Spring Boot 3.5.16**
- **Spring MVC com Thymeleaf** (`spring-boot-starter-thymeleaf` + `thymeleaf-extras-springsecurity6`) — renderização no servidor (SSR); **proibido** usar SPA/React/Angular.
- **Spring Data JPA com Hibernate 6** (`spring.jpa.hibernate.ddl-auto=validate`).
- **Flyway** (`flyway-core` + `flyway-database-postgresql`):
  - `src/main/resources/db/migration/`: migrations estruturais e carga de perfis (`dev` e `prod`).
  - `src/main/resources/db/dev/` (`R__*`): seeds repetíveis exclusivos de desenvolvimento (`SPRING_PROFILES_ACTIVE=dev`).
- **Banco de Dados:** PostgreSQL 16 (via Docker Compose em `dev`/`prod`) e H2 permitido **apenas** em escopo `test` (`<scope>test</scope>`).
- **Segurança:** Spring Security 6 (`SecurityFilterChain`, `UserDetailsService`, `BCryptPasswordEncoder`) + JWT (`com.auth0:java-jwt`) em Cookies `HttpOnly` (`jwt_token_eventif` e `jwt_refresh_token_eventif`) + Refresh Token persistido.
