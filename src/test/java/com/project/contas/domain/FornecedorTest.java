package com.project.contas.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FornecedorTest {

    @Test
    public void cadastrarFornecedor_quandoNomeValido_criaFornecedorCorretamente() {
        String nome = "Fornecedor Teste";
        Fornecedor fornecedor = Fornecedor.cadastrarFornecedor(nome);

        assertNotNull(fornecedor);
        assertNotNull(fornecedor.getId());
        assertEquals(nome, fornecedor.getNome());
    }
}
