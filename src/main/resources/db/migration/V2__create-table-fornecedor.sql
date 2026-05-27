CREATE TABLE IF NOT EXISTS fornecedor (
    id UUID NOT NULL,
    nome VARCHAR(255) NOT NULL,
    CONSTRAINT pk_fornecedor PRIMARY KEY (id)
);
