package com.project.contas.application.service;

import com.project.contas.application.exception.RegraNegocioException;
import com.project.contas.domain.ImportacaoConta;
import com.project.contas.domain.dto.ImportacaoContaDTO;
import com.project.contas.domain.repository.ImportacaoContaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscarImportacaoContaAppServiceTest {

    @InjectMocks
    private BuscarImportacaoContaAppService buscarImportacaoContaAppService;

    @Mock
    private ImportacaoContaRepository importacaoContaRepository;

    @Test
    public void executar_quandoImportacaoExiste_retornaDTO() {
        ImportacaoConta importacao = ImportacaoConta.iniciar();
        when(importacaoContaRepository.findById(importacao.getId())).thenReturn(Optional.of(importacao));

        ImportacaoContaDTO resultado = buscarImportacaoContaAppService.executar(importacao.getId());

        assertEquals(importacao.getId(), resultado.id());
        assertEquals(importacao.getStatus(), resultado.status());
    }

    @Test
    public void executar_quandoImportacaoNaoExiste_lancaExcecao() {
        UUID id = UUID.randomUUID();
        when(importacaoContaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RegraNegocioException.class, () -> buscarImportacaoContaAppService.executar(id));
    }
}
