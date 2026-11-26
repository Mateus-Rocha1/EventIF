CREATE TABLE inscricoes (
    id BIGSERIAL PRIMARY KEY,
    data_hora TIMESTAMP NOT NULL,
    presente BOOLEAN NOT NULL DEFAULT FALSE,
    usuario_id BIGINT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    atividade_id BIGINT NOT NULL REFERENCES atividades(id) ON DELETE CASCADE,
    CONSTRAINT uk_inscricao_usuario_atividade UNIQUE (usuario_id, atividade_id)
);
