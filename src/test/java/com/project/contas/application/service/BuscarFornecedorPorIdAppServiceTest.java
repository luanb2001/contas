package com.project.contas.application.service;

import com.project.contas.application.exception.RegraNegocioException;
import com.project.contas.domain.Fornecedor;
import com.project.contas.domain.dto.FornecedorDTO;
import com.project.contas.domain.repository.FornecedorRepository;
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
public class BuscarFornecedorPorIdAppServiceTest {

    @InjectMocks
    private BuscarFornecedorPorIdAppService buscarFornecedorPorIdAppService;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @Test
    public void executar_quandoFornecedorExiste_retornaDTO() {
        Fornecedor fornecedor = Fornecedor.cadastrarFornecedor("Fornecedor Teste");
        when(this.fornecedorRepository.findById(fornecedor.getId())).thenReturn(Optional.of(fornecedor));

        FornecedorDTO resultado = this.buscarFornecedorPorIdAppService.executar(fornecedor.getId());

        assertEquals(fornecedor.getId(), resultado.id());
        assertEquals(fornecedor.getNome(), resultado.nome());
    }

    @Test
    public void executar_quandoFornecedorNaoExiste_lancaExcecao() {
        UUID id = UUID.randomUUID();
        when(this.fornecedorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RegraNegocioException.class, () -> this.buscarFornecedorPorIdAppService.executar(id));
    }
}
