CREATE TABLE eventos (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    local VARCHAR(255) NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    categoria_id BIGINT NOT NULL REFERENCES categorias(id),
    organizador_id BIGINT NOT NULL REFERENCES usuarios(id)
);
