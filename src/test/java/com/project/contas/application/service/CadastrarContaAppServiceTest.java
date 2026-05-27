package com.project.contas.application.service;

import com.project.contas.domain.Conta;
import com.project.contas.domain.Fornecedor;
import com.project.contas.domain.dto.CadastrarContaDTO;
import com.project.contas.domain.enums.SituacaoContaEnum;
import com.project.contas.domain.repository.ContaRepository;
import com.project.contas.domain.repository.FornecedorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CadastrarContaAppServiceTest {

    @InjectMocks
    private CadastrarContaAppService cadastrarContaAppService;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @Test
    public void executar() {
        Fornecedor fornecedor = Fornecedor.cadastrarFornecedor("Fornecedor Teste");
        when(fornecedorRepository.findById(fornecedor.getId())).thenReturn(Optional.of(fornecedor));

        CadastrarContaDTO cadastrarContaDTO = new CadastrarContaDTO(
                LocalDateTime.now().plusDays(10),
                LocalDateTime.now(),
                "Conta de água",
                SituacaoContaEnum.ABERTA,
                BigDecimal.valueOf(100),
                fornecedor.getId()
        );

        assertDoesNotThrow(() -> this.cadastrarContaAppService.executar(cadastrarContaDTO));

        verify(this.contaRepository).save(any(Conta.class));
    }
}
