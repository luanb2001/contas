package com.project.contas.adapters.in.handler;

import java.time.LocalDateTime;

public record ApiErrorResponse(
        String mensagem,
        LocalDateTime timestamp
) {
}
