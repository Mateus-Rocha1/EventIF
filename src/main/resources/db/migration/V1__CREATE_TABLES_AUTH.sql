CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    roles VARCHAR(50) NOT NULL
);

CREATE TABLE refresh_token (
    id BIGSERIAL PRIMARY KEY,
    refresh_token UUID NOT NULL UNIQUE,
    user_id BIGINT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    expires_at TIMESTAMP NOT NULL,
    session_expires_at TIMESTAMP NOT NULL
);
