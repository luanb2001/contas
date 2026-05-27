ALTER TABLE conta
    ADD COLUMN fornecedor_id UUID;

ALTER TABLE conta
    ADD CONSTRAINT fk_conta_fornecedor
    FOREIGN KEY (fornecedor_id)
    REFERENCES fornecedor(id);

ALTER TABLE conta
    ALTER COLUMN fornecedor_id SET NOT NULL;
