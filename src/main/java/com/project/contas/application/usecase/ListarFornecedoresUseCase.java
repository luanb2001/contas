package com.project.contas.application.usecase;

import com.project.contas.domain.dto.FornecedorDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ListarFornecedoresUseCase {
    List<FornecedorDTO> executar(Pageable pageable);
}
