package com.project.contas.application.service;

import com.project.contas.domain.dto.FornecedorDTO;
import com.project.contas.domain.repository.FornecedorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarFornecedoresAppServiceTest {

    @InjectMocks
    private ListarFornecedoresAppService listarFornecedoresAppService;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @Test
    public void executar_retornaListaPaginada() {
        List<FornecedorDTO> esperado = List.of(
                new FornecedorDTO(UUID.randomUUID(), "Fornecedor A"),
                new FornecedorDTO(UUID.randomUUID(), "Fornecedor B")
        );
        when(fornecedorRepository.listarFornecedores(any())).thenReturn(esperado);

        List<FornecedorDTO> resultado = listarFornecedoresAppService.executar(PageRequest.of(0, 10));

        assertEquals(2, resultado.size());
        assertEquals("Fornecedor A", resultado.get(0).nome());
    }

    @Test
    public void executar_semRegistros_retornaListaVazia() {
        when(fornecedorRepository.listarFornecedores(any())).thenReturn(List.of());

        List<FornecedorDTO> resultado = listarFornecedoresAppService.executar(PageRequest.of(0, 10));

        assertEquals(0, resultado.size());
    }
}
