-- 1. Categorias de exemplo (RT04: 2 categorias)
INSERT INTO categorias (nome)
SELECT 'Tecnologia e Computação'
WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE nome = 'Tecnologia e Computação');

INSERT INTO categorias (nome)
SELECT 'Ciência e Inovação'
WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE nome = 'Ciência e Inovação');

-- 2. Eventos de exemplo (RT04: 2 eventos vinculados ao organizador de teste)
INSERT INTO eventos (titulo, descricao, local, data_inicio, data_fim, status, categoria_id, organizador_id)
SELECT
    'Semana da Computação IFCE',
    'Semana acadêmica com palestras, minicursos e oficinas práticas de desenvolvimento de software.',
    'Auditório Central — IFCE Campus Fortaleza',
    DATE '2026-12-10',
    DATE '2026-12-12',
    'PUBLICADO',
    c.id,
    u.id
FROM categorias c, usuarios u
WHERE c.nome = 'Tecnologia e Computação'
  AND u.email = 'organizador@eventif.com'
  AND NOT EXISTS (SELECT 1 FROM eventos WHERE titulo = 'Semana da Computação IFCE');

INSERT INTO eventos (titulo, descricao, local, data_inicio, data_fim, status, categoria_id, organizador_id)
SELECT
    'Jornada de Ciência e Inovação',
    'Encontro científico para apresentação de pesquisas, oficinas e debates sobre inovação tecnológica.',
    'Bloco Didático — IFCE Campus Fortaleza',
    DATE '2026-12-15',
    DATE '2026-12-16',
    'PUBLICADO',
    c.id,
    u.id
FROM categorias c, usuarios u
WHERE c.nome = 'Ciência e Inovação'
  AND u.email = 'organizador@eventif.com'
  AND NOT EXISTS (SELECT 1 FROM eventos WHERE titulo = 'Jornada de Ciência e Inovação');

-- 3. Atividades do Evento 1 (Semana da Computação IFCE)
INSERT INTO atividades (titulo, tipo, ministrante, inicio, fim, carga_horaria, vagas, evento_id)
SELECT
    'Arquitetura Web com Spring Boot 3 e Java 21',
    'PALESTRA',
    'Prof. Mateus Rocha',
    TIMESTAMP '2026-12-10 09:00:00',
    TIMESTAMP '2026-12-10 11:00:00',
    2,
    50,
    e.id
FROM eventos e
WHERE e.titulo = 'Semana da Computação IFCE'
  AND NOT EXISTS (SELECT 1 FROM atividades WHERE titulo = 'Arquitetura Web com Spring Boot 3 e Java 21');

INSERT INTO atividades (titulo, tipo, ministrante, inicio, fim, carga_horaria, vagas, evento_id)
SELECT
    'Desenvolvimento Web Server-Side com Thymeleaf',
    'MINICURSO',
    'Profa. Ana Beatriz',
    TIMESTAMP '2026-12-11 14:00:00',
    TIMESTAMP '2026-12-11 18:00:00',
    4,
    30,
    e.id
FROM eventos e
WHERE e.titulo = 'Semana da Computação IFCE'
  AND NOT EXISTS (SELECT 1 FROM atividades WHERE titulo = 'Desenvolvimento Web Server-Side com Thymeleaf');

-- 4. Atividades do Evento 2 (Jornada de Ciência e Inovação)
INSERT INTO atividades (titulo, tipo, ministrante, inicio, fim, carga_horaria, vagas, evento_id)
SELECT
    'Inteligência Artificial Aplicada à Educação',
    'PALESTRA',
    'Dr. Carlos Eduardo',
    TIMESTAMP '2026-12-15 10:00:00',
    TIMESTAMP '2026-12-15 12:00:00',
    2,
    60,
    e.id
FROM eventos e
WHERE e.titulo = 'Jornada de Ciência e Inovação'
  AND NOT EXISTS (SELECT 1 FROM atividades WHERE titulo = 'Inteligência Artificial Aplicada à Educação');

INSERT INTO atividades (titulo, tipo, ministrante, inicio, fim, carga_horaria, vagas, evento_id)
SELECT
    'Oficina Prática de Docker e PostgreSQL',
    'OFICINA',
    'Eng. Marcos Vinícius',
    TIMESTAMP '2026-12-16 14:00:00',
    TIMESTAMP '2026-12-16 17:00:00',
    3,
    25,
    e.id
FROM eventos e
WHERE e.titulo = 'Jornada de Ciência e Inovação'
  AND NOT EXISTS (SELECT 1 FROM atividades WHERE titulo = 'Oficina Prática de Docker e PostgreSQL');
