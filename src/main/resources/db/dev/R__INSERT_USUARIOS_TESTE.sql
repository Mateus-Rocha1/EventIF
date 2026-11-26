CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO usuarios (criado_em, atualizado_em, nome, email, senha, ativo)
VALUES
    (NOW(), NOW(), 'Administrador Teste', 'admin@eventif.com', crypt('admin123', gen_salt('bf', 10)), TRUE),
    (NOW(), NOW(), 'Organizador Teste', 'organizador@eventif.com', crypt('org123', gen_salt('bf', 10)), TRUE),
    (NOW(), NOW(), 'Participante Teste', 'participante@eventif.com', crypt('part123', gen_salt('bf', 10)), TRUE)
ON CONFLICT (email) DO NOTHING;

INSERT INTO usuario_perfil (usuario_id, perfil_id)
SELECT u.id, p.id FROM usuarios u, perfis p
WHERE u.email = 'admin@eventif.com' AND p.nome = 'ROLE_ADMIN'
ON CONFLICT DO NOTHING;

INSERT INTO usuario_perfil (usuario_id, perfil_id)
SELECT u.id, p.id FROM usuarios u, perfis p
WHERE u.email = 'organizador@eventif.com' AND p.nome = 'ROLE_ORGANIZADOR'
ON CONFLICT DO NOTHING;

INSERT INTO usuario_perfil (usuario_id, perfil_id)
SELECT u.id, p.id FROM usuarios u, perfis p
WHERE u.email = 'participante@eventif.com' AND p.nome = 'ROLE_PARTICIPANTE'
ON CONFLICT DO NOTHING;
