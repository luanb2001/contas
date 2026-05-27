package com.project.contas.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record CadastrarFornecedorDTO(
        @NotBlank String nome
) {
}
