package com.project.contas.application.usecase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface CarregarValorPagoUseCase {

    BigDecimal executar(LocalDateTime dataInicial, LocalDateTime dataFinal);
}
