package com.project.contas.domain.dto;

import java.util.UUID;

public record ImportarContasCSVMessage(
        UUID importacaoId,
        String nomeArquivo,
        String conteudoBase64
) {
}
