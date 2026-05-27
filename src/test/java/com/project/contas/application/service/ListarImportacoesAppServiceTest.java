package com.project.contas.application.service;

import com.project.contas.domain.dto.ImportacaoContaDTO;
import com.project.contas.domain.enums.StatusImportacaoEnum;
import com.project.contas.domain.repository.ImportacaoContaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarImportacoesAppServiceTest {

    @InjectMocks
    private ListarImportacoesAppService listarImportacoesAppService;

    @Mock
    private ImportacaoContaRepository importacaoContaRepository;

    @Test
    public void executar_retornaListaPaginada() {
        List<ImportacaoContaDTO> esperado = List.of(
                new ImportacaoContaDTO(UUID.randomUUID(), StatusImportacaoEnum.FINALIZADA,
                        LocalDateTime.now(), 10, 10, 0, null, null),
                new ImportacaoContaDTO(UUID.randomUUID(), StatusImportacaoEnum.FINALIZADA_COM_ERROS,
                        LocalDateTime.now(), 5, 3, 2, null, "Linha 2: valor nulo")
        );
        when(importacaoContaRepository.listarImportacoes(any())).thenReturn(esperado);

        List<ImportacaoContaDTO> resultado = listarImportacoesAppService.executar(PageRequest.of(0, 10));

        assertEquals(2, resultado.size());
        assertEquals(StatusImportacaoEnum.FINALIZADA, resultado.get(0).status());
        assertEquals("Linha 2: valor nulo", resultado.get(1).detalhesErros());
    }

    @Test
    public void executar_semRegistros_retornaListaVazia() {
        when(importacaoContaRepository.listarImportacoes(any())).thenReturn(List.of());

        List<ImportacaoContaDTO> resultado = listarImportacoesAppService.executar(PageRequest.of(0, 10));

        assertEquals(0, resultado.size());
    }
}
