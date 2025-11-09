-- SCRIPT DE ESTRUTURA (DDL) UNIFICADO - PROJETO AIRDATA
-- Este script cria a extensão, todas as tabelas, chaves e índices.
-- Execute este script EM PRIMEIRO LUGAR em um banco de dados limpo.

BEGIN;

-- 1. CRIAÇÃO DA EXTENSÃO (do CREATE_extension_unaccent.sql)
CREATE EXTENSION IF NOT EXISTS unaccent;

-- 2. CRIAÇÃO DAS TABELAS (do airdata_schema.sql)

CREATE TABLE IF NOT EXISTS public.localizacao
(
    id_localizacao serial NOT NULL,
    nome_cidade character varying(100) NOT NULL,
    estado character varying(50) NOT NULL,
    pais character varying(50) NOT NULL,
    latitude numeric(9, 6),
    longitude numeric(9, 6),
    PRIMARY KEY (id_localizacao)
);

CREATE TABLE IF NOT EXISTS public.poluente
(
    id_poluente serial,
    nome character varying(20) NOT NULL,
    descricao text,
    PRIMARY KEY (id_poluente),
    UNIQUE (nome)
);

CREATE TABLE IF NOT EXISTS public.nivel_risco
(
    id_nivel_risco serial,
    classificacao character varying(50) NOT NULL,
    descricao_efeitos text,
    aqi_min integer NOT NULL,
    aqi_max integer NOT NULL,
    PRIMARY KEY (id_nivel_risco),
    UNIQUE (classificacao)
);

CREATE TABLE IF NOT EXISTS public.recomendacao
(
    id_recomendacao serial,
    texto_recomendacao text NOT NULL,
    PRIMARY KEY (id_recomendacao)
);

CREATE TABLE IF NOT EXISTS public.medicao
(
    id_medicao serial,
    valor_aqi integer NOT NULL,
    data_hora timestamp with time zone NOT NULL,
    id_localizacao integer NOT NULL,
    id_poluente_dominante integer NOT NULL,
    PRIMARY KEY (id_medicao)
);

CREATE TABLE IF NOT EXISTS public.risco_recomendacao
(
    id_nivel_risco integer NOT NULL,
    id_recomendacao integer NOT NULL,
    PRIMARY KEY (id_nivel_risco, id_recomendacao)
);

-- 3. CRIAÇÃO DAS CHAVES ESTRANGEIRAS (do airdata_schema.sql)

ALTER TABLE IF EXISTS public.medicao
    ADD FOREIGN KEY (id_localizacao)
    REFERENCES public.localizacao (id_localizacao) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

ALTER TABLE IF EXISTS public.medicao
    ADD FOREIGN KEY (id_poluente_dominante)
    REFERENCES public.poluente (id_poluente) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

ALTER TABLE IF EXISTS public.risco_recomendacao
    ADD FOREIGN KEY (id_nivel_risco)
    REFERENCES public.nivel_risco (id_nivel_risco) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

ALTER TABLE IF EXISTS public.risco_recomendacao
    ADD FOREIGN KEY (id_recomendacao)
    REFERENCES public.recomendacao (id_recomendacao) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

-- 4. CRIAÇÃO DOS ÍNDICES (do CREATE_index_fk_medicao_localizacao_data.sql)

-- Cria um índice na chave estrangeira que liga a medição à localização
CREATE INDEX IF NOT EXISTS idx_medicao_localizacao ON medicao(id_localizacao);

-- Cria um índice na coluna de data e hora para otimizar buscas por período
CREATE INDEX IF NOT EXISTS idx_medicao_data_hora ON medicao(data_hora);

END;
