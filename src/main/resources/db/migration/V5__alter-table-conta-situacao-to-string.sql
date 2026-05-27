ALTER TABLE conta ADD COLUMN situacao_str VARCHAR(50);

UPDATE conta SET situacao_str = CASE
    WHEN situacao = 0 THEN 'ABERTA'
    WHEN situacao = 1 THEN 'VENCIDA'
    WHEN situacao = 2 THEN 'CANCELADA'
    WHEN situacao = 3 THEN 'PAGA'
    ELSE 'ABERTA'
END;

ALTER TABLE conta DROP COLUMN situacao;
ALTER TABLE conta RENAME COLUMN situacao_str TO situacao;
ALTER TABLE conta ALTER COLUMN situacao SET NOT NULL;
