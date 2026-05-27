package com.project.contas.application.service;

import com.project.contas.application.exception.RegraNegocioException;
import com.project.contas.domain.Fornecedor;
import com.project.contas.domain.repository.FornecedorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeletarFornecedorAppServiceTest {

    @InjectMocks
    private DeletarFornecedorAppService deletarFornecedorAppService;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @Test
    public void executar_quandoFornecedorExiste_deleta() {
        Fornecedor fornecedor = Fornecedor.cadastrarFornecedor("Fornecedor Teste");
        when(fornecedorRepository.findById(fornecedor.getId())).thenReturn(Optional.of(fornecedor));

        assertDoesNotThrow(() -> deletarFornecedorAppService.executar(fornecedor.getId()));

        verify(fornecedorRepository).deleteById(fornecedor.getId());
    }

    @Test
    public void executar_quandoFornecedorNaoExiste_lancaExcecao() {
        UUID id = UUID.randomUUID();
        when(fornecedorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RegraNegocioException.class, () -> deletarFornecedorAppService.executar(id));
    }
}
