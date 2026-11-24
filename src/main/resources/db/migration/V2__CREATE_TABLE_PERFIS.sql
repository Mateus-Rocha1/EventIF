CREATE TABLE perfis (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE usuario_perfil (
    usuario_id BIGINT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    perfil_id BIGINT NOT NULL REFERENCES perfis(id) ON DELETE CASCADE,
    PRIMARY KEY (usuario_id, perfil_id)
);
