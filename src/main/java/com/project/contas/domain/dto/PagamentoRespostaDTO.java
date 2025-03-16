package com.project.contas.domain.dto;

import com.project.contas.domain.enums.StatusPagamentoEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record PagamentoRespostaDTO(
        UUID pagamentoId,
        UUID contaId,
        LocalDateTime dataProcessamento,
        StatusPagamentoEnum status,
        String mensagem
) {}
