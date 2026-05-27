package com.project.contas.domain.dto;

import com.project.contas.domain.Fornecedor;

import java.util.UUID;

public record FornecedorDTO(
        UUID id,
        String nome
) {
    public static FornecedorDTO fromEntity(Fornecedor fornecedor) {
        return new FornecedorDTO(fornecedor.getId(), fornecedor.getNome());
    }
}
