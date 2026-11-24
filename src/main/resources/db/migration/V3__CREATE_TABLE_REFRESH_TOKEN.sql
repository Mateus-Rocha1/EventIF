CREATE TABLE refresh_token (
    id BIGSERIAL PRIMARY KEY,
    refresh_token UUID NOT NULL UNIQUE,
    user_id BIGINT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    expires_at TIMESTAMP NOT NULL,
    session_expires_at TIMESTAMP NOT NULL
);
