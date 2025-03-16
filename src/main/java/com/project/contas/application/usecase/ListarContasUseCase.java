package com.project.contas.application.usecase;

import com.project.contas.domain.dto.ContaDTO;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ListarContasUseCase {

    List<ContaDTO> executar(LocalDateTime dataVencimentoInicial, LocalDateTime dataVencimentoFinal, String descricao, Pageable pageable);
}
