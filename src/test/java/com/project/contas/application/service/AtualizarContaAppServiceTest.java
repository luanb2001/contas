package com.project.contas.application.service;

import com.project.contas.domain.dto.ContaDTO;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class AtualizarContaAppServiceTest {

    @InjectMocks
    private AtualizarContaAppService atualizarContaAppService;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @Test
    public void executar_quandoContaExiste_atualizaDados() {
        UUID fornecedorId = UUID.randomUUID();
        ContaDTO contaDTO = new ContaDTO(
                UUID.randomUUID(),
                LocalDateTime.now().plusDays(10),
                LocalDateTime.now(),
                "Conta de água",
                SituacaoContaEnum.ABERTA,
                BigDecimal.valueOf(100),
                fornecedorId,
                "Fornecedor Teste"
        );

        assertDoesNotThrow(() -> this.atualizarContaAppService.executar(contaDTO));
    }
}
