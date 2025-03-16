package com.project.contas.application.service;

import com.project.contas.domain.enums.SituacaoContaEnum;
import com.project.contas.domain.repository.ContaRepository;
import com.project.contas.domain.dto.AtualizarSituacaoContaDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class AtualizarSituacaoContaAppServiceTest {

    @InjectMocks
    private AtualizarSituacaoContaAppService atualizarSituacaoContaAppService;

    @Mock
    private ContaRepository contaRepository;

    @Test
    void executar() {
        AtualizarSituacaoContaDTO atualizarSituacaoContaDTO = new AtualizarSituacaoContaDTO(
                UUID.randomUUID(),
                SituacaoContaEnum.PAGA
        );

        assertDoesNotThrow(() -> this.atualizarSituacaoContaAppService.executar(atualizarSituacaoContaDTO));
    }
}
