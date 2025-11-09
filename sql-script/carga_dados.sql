-- SCRIPT DE CARGA DE DADOS (DML) UNIFICADO - PROJETO AIRDATA
-- Este script popula todas as tabelas com dados de consulta e exemplos.
-- Execute este script EM SEGUNDO LUGAR, após o schema.sql.

BEGIN;

-- Limpa os dados antigos para garantir uma carga limpa
DELETE FROM public.medicao;
DELETE FROM public.risco_recomendacao;
DELETE FROM public.recomendacao;
DELETE FROM public.nivel_risco;
DELETE FROM public.poluente;
DELETE FROM public.localizacao;

-- 1. DADOS DA TABELA 'localizacao' (da Sprint 1 / INSERT_tabela_location.sql)
INSERT INTO localizacao (nome_cidade, estado, pais, latitude, longitude) VALUES
('São Paulo', 'SP', 'Brazil', -23.544846, -46.627676),
('Osasco', 'SP', 'Brazil', -23.526721, -46.792078),
('Campinas', 'SP', 'Brazil', -22.902525, -47.057211),
('Santos', 'SP', 'Brazil', -23.963057, -46.321170),
('Guarulhos', 'SP', 'Brazil', -23.455534, -46.518533),
('São Bernardo do Campo', 'SP', 'Brazil', -23.698671, -46.546232),
('São José dos Campos', 'SP', 'Brazil', -23.187887, -45.871198),
('Ribeirão Preto', 'SP', 'Brazil', -21.177066, -47.818988),
('Sorocaba', 'SP', 'Brazil', -23.502427, -47.479030),
('Mauá', 'SP', 'Brazil', -23.668549, -46.466000),
('Jundiaí', 'SP', 'Brazil', -23.192004, -46.897097),
('Carapicuíba', 'SP', 'Brazil', -23.531395, -46.835780),
('Piracicaba', 'SP', 'Brazil', -22.701222, -47.649653),
('Bauru', 'SP', 'Brazil', -22.326608, -49.092759),
('Cubatão', 'SP', 'Brazil', -23.879027, -46.418483),
('Taubaté', 'SP', 'Brazil', -23.032351, -45.575805),
('Tatuí', 'SP', 'Brazil', -23.360752, -47.870799),
('Americana', 'SP', 'Brazil', -22.724507, -47.347456),
('Limeira', 'SP', 'Brazil', -22.563604, -47.414314),
('São Sebastião', 'SP', 'Brazil', -23.805226, -45.400052),
('Presidente Prudente', 'SP', 'Brazil', -22.119937, -51.408777),
('São José do Rio Preto', 'SP', 'Brazil', -20.784689, -49.398278);

-- 2. DADOS DA TABELA 'poluente' (da Sprint 1 / INSERT_tabela_poluente.sql)
INSERT INTO poluente (nome, descricao) VALUES
('pm25', 'Material Particulado fino (MP2.5) com diâmetro de 2.5 micrômetros ou menos. Pode penetrar profundamente nos pulmões e entrar na corrente sanguínea.'),
('pm10', 'Material Particulado (MP10) com diâmetro de 10 micrômetros ou menos. Partículas inaláveis que podem causar problemas respiratórios.'),
('o3', 'Ozônio (O₃). Gás que, ao nível do solo, é um poluente nocivo que pode causar irritação no sistema respiratório.'),
('co', 'Monóxido de Carbono (CO). Gás incolor e inodoro, tóxico em altas concentrações por reduzir a capacidade do sangue de transportar oxigênio.'),
('no2', 'Dióxido de Nitrogênio (NO₂). Gás poluente associado à queima de combustíveis, que contribui para a formação de chuvas ácidas e problemas respiratórios.'),
('so2', 'Dióxido de Enxofre (SO₂). Gás irritante que afeta principalmente o sistema respiratório, associado à queima de combustíveis fósseis contendo enxofre.')
ON CONFLICT (nome) DO UPDATE 
SET descricao = EXCLUDED.descricao;

-- 3. DADOS DAS TABELAS 'nivel_risco', 'recomendacao' E 'risco_recomendacao' (da Sprint 2 / INSERT_inital.sql)
INSERT INTO nivel_risco (id_nivel_risco, classificacao, aqi_min, aqi_max, descricao_efeitos) VALUES
(1, 'Boa', 0, 40, 'Qualidade do ar boa. Nenhum efeito à saúde.'),
(2, 'Moderada', 41, 80, 'Pessoas de grupos sensíveis (crianças, idosos e pessoas com doenças respiratórias e cardíacas) podem apresentar sintomas como tosse seca e cansaço. A população em geral não é afetada.'),
(3, 'Ruim', 81, 120, 'Toda a população pode apresentar sintomas como tosse seca, cansaço, ardor nos olhos, nariz e garganta. Pessoas de grupos sensíveis podem apresentar efeitos mais sérios na saúde.'),
(4, 'Muito Ruim', 121, 200, 'Toda a população pode apresentar agravamento dos sintomas como tosse seca, cansaço, ardor nos olhos, nariz e garganta e ainda falta de ar e respiração ofegante. Efeitos ainda mais graves à saúde de grupos sensíveis.'),
(5, 'Péssima', 201, 400, 'Toda a população pode apresentar sérios riscos de manifestações de doenças respiratórias e cardiovasculares. Aumento de mortes prematuras em pessoas de grupos sensíveis.')
ON CONFLICT (id_nivel_risco) DO UPDATE 
SET classificacao = EXCLUDED.classificacao, aqi_min = EXCLUDED.aqi_min, aqi_max = EXCLUDED.aqi_max, descricao_efeitos = EXCLUDED.descricao_efeitos;

INSERT INTO recomendacao (id_recomendacao, texto_recomendacao) VALUES
(1, 'Grupos sensíveis: reduzir atividades físicas intensas ao ar livre.'),
(2, 'População em geral: reduzir atividades físicas intensas ao ar livre.'),
(3, 'Grupos sensíveis: evitar completamente atividades ao ar livre.'),
(4, 'Beba bastante água para manter as vias respiratórias hidratadas.'),
(5, 'Mantenha as janelas de casa fechadas.'),
(6, 'População em geral: evitar qualquer atividade ao ar livre.'),
(7, 'Grupos sensíveis: permanecer obrigatoriamente em ambientes internos.'),
(8, 'Se for estritamente necessário sair, considere o uso de máscaras de proteção (tipo PFF2).'),
(9, 'Procure atendimento médico imediato em caso de falta de ar ou mal-estar.')
ON CONFLICT (id_recomendacao) DO UPDATE 
SET texto_recomendacao = EXCLUDED.texto_recomendacao;

INSERT INTO risco_recomendacao (id_nivel_risco, id_recomendacao) VALUES
(2, 1), (3, 2), (3, 3), (3, 4), (4, 3), (4, 4), (4, 5), (4, 6), (4, 7), (5, 5), (5, 6), (5, 7), (5, 8), (5, 9)
ON CONFLICT (id_nivel_risco, id_recomendacao) DO NOTHING;

-- 4. DADOS HISTÓRICOS DA TABELA 'medicao' (da Sprint 3 / Simulação)
-- Assumindo que o ID de 'São Paulo' é 1 e 'Osasco' é 2
-- E que 'pm10' é ID 2 e 'pm25' é ID 1 (com base na ordem de inserção acima)
INSERT INTO public.medicao (valor_aqi, data_hora, id_localizacao, id_poluente_dominante) VALUES
(55,  (NOW() - INTERVAL '6 days'), 1, 2), -- SP, 6 dias atrás
(68,  (NOW() - INTERVAL '5 days'), 1, 1), -- SP, 5 dias atrás
(82,  (NOW() - INTERVAL '4 days'), 1, 1), -- SP, 4 dias atrás
(110, (NOW() - INTERVAL '3 days'), 1, 2), -- SP, 3 dias atrás
(95,  (NOW() - INTERVAL '2 days'), 1, 1), -- SP, 2 dias atrás
(70,  (NOW() - INTERVAL '1 day'), 1, 2), -- SP, 1 dia atrás
(66,  (NOW()), 1, 2),                     -- SP, Hoje
(45,  (NOW() - INTERVAL '6 days'), 2, 2), -- Osasco, 6 dias atrás
(52,  (NOW() - INTERVAL '5 days'), 2, 1), -- Osasco, 5 dias atrás
(60,  (NOW() - INTERVAL '4 days'), 2, 1), -- Osasco, 4 dias atrás
(75,  (NOW() - INTERVAL '3 days'), 2, 2), -- Osasco, 3 dias atrás
(55,  (NOW() - INTERVAL '2 days'), 2, 1), -- Osasco, 2 dias atrás
(48,  (NOW() - INTERVAL '1 day'), 2, 2), -- Osasco, 1 dia atrás
(50,  (NOW()), 2, 2);                     -- Osasco, Hoje

COMMIT;
