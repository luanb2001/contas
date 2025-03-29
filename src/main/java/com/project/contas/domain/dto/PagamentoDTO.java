package com.project.contas.domain.dto;

import com.project.contas.domain.enums.StatusPagamentoEnum;
import java.time.LocalDateTime;
import java.util.UUID;

public record PagamentoDTO(
        UUID id,
        UUID contaId,
        LocalDateTime dataCriacao,
        LocalDateTime dataProcessamento,
        Double valor,
        StatusPagamentoEnum status,
        String motivoRecusa
) {}

