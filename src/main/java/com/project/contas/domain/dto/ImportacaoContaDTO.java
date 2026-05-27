package com.project.contas.domain.dto;

import com.project.contas.domain.ImportacaoConta;
import com.project.contas.domain.enums.StatusImportacaoEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record ImportacaoContaDTO(
        UUID id,
        StatusImportacaoEnum status,
        LocalDateTime dataCriacao,
        Integer totalRegistros,
        Integer totalProcessados,
        Integer totalFalhas,
        String mensagemErro,
        String detalhesErros
) {
    public static ImportacaoContaDTO fromEntity(ImportacaoConta importacao) {
        return new ImportacaoContaDTO(
                importacao.getId(),
                importacao.getStatus(),
                importacao.getDataCriacao(),
                importacao.getTotalRegistros(),
                importacao.getTotalProcessados(),
                importacao.getTotalFalhas(),
                importacao.getMensagemErro(),
                importacao.getDetalhesErros()
        );
    }
}
