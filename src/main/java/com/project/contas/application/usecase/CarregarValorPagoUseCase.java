package com.project.contas.application.usecase;

import java.time.LocalDateTime;

public interface CarregarValorPagoUseCase {

    Double executar(LocalDateTime dataInicial, LocalDateTime dataFinal);
}
