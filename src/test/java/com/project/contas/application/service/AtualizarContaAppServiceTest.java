package com.project.contas.application.service;

import com.project.contas.domain.repository.ContaRepository;
import com.project.contas.domain.dto.ContaDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class AtualizarContaAppServiceTest {

    @InjectMocks
    private AtualizarContaAppService atualizarContaAppService;

    @Mock
    private ContaRepository contaRepository;

    @Test
    public void executar() {
        ContaDTO contaDTO = new ContaDTO(
                UUID.randomUUID(),
                LocalDateTime.now().plusDays(10),
                LocalDateTime.now(),
                "Conta de água",
                null,
                100.00
        );

        assertDoesNotThrow(() -> this.atualizarContaAppService.executar(contaDTO));
    }
}
