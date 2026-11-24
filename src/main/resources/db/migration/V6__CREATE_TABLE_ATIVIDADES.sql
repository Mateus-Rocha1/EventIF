CREATE TABLE atividades (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    ministrante VARCHAR(255) NOT NULL,
    inicio TIMESTAMP NOT NULL,
    fim TIMESTAMP NOT NULL,
    carga_horaria INTEGER NOT NULL,
    vagas INTEGER NOT NULL,
    evento_id BIGINT NOT NULL REFERENCES eventos(id) ON DELETE CASCADE
);
