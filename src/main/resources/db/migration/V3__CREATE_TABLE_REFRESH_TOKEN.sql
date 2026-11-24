CREATE TABLE refresh_token (
    id BIGSERIAL PRIMARY KEY,
    refresh_token UUID NOT NULL UNIQUE,
    usuario_id BIGINT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    expira_em TIMESTAMP NOT NULL,
    sessao_expira_em TIMESTAMP NOT NULL
);
