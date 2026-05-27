package com.project.contas.application.service;

import com.project.contas.application.exception.RegraNegocioException;
import com.project.contas.domain.Fornecedor;
import com.project.contas.domain.dto.CadastrarFornecedorDTO;
import com.project.contas.domain.repository.FornecedorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AtualizarFornecedorAppServiceTest {

    @InjectMocks
    private AtualizarFornecedorAppService atualizarFornecedorAppService;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @Test
    public void executar_quandoFornecedorExiste_atualizaNome() {
        Fornecedor fornecedor = Fornecedor.cadastrarFornecedor("Nome Antigo");
        CadastrarFornecedorDTO dto = new CadastrarFornecedorDTO("Nome Novo");
        when(fornecedorRepository.findById(fornecedor.getId())).thenReturn(Optional.of(fornecedor));

        atualizarFornecedorAppService.executar(fornecedor.getId(), dto);

        assertEquals("Nome Novo", fornecedor.getNome());
        verify(fornecedorRepository).save(any(Fornecedor.class));
    }

    @Test
    public void executar_quandoFornecedorNaoExiste_lancaExcecao() {
        UUID id = UUID.randomUUID();
        when(fornecedorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RegraNegocioException.class,
                () -> atualizarFornecedorAppService.executar(id, new CadastrarFornecedorDTO("Nome")));
    }
}
