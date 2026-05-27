CREATE TABLE IF NOT EXISTS importacao_conta (
    id UUID NOT NULL,
    status VARCHAR(50) NOT NULL,
    data_criacao TIMESTAMP,
    data_inicio_processamento TIMESTAMP,
    data_fim_processamento TIMESTAMP,
    total_registros INT,
    total_processados INT,
    total_falhas INT,
    mensagem_erro TEXT,
    CONSTRAINT pk_importacao_conta PRIMARY KEY (id)
);
