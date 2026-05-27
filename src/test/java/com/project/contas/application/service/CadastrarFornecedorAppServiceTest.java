package com.project.contas.application.service;

import com.project.contas.domain.Fornecedor;
import com.project.contas.domain.dto.CadastrarFornecedorDTO;
import com.project.contas.domain.repository.FornecedorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CadastrarFornecedorAppServiceTest {

    @InjectMocks
    private CadastrarFornecedorAppService cadastrarFornecedorAppService;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @Test
    public void executar_quandoDadosValidos_salvaNoBanco() {
        CadastrarFornecedorDTO dto = new CadastrarFornecedorDTO("Fornecedor Teste");

        assertDoesNotThrow(() -> this.cadastrarFornecedorAppService.executar(dto));

        verify(this.fornecedorRepository).save(any(Fornecedor.class));
    }
}
