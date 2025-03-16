package com.project.contas.application.service;

import com.project.contas.domain.Conta;
import com.project.contas.domain.repository.ContaRepository;
import com.project.contas.domain.dto.CadastrarContaDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CadastrarContaAppServiceTest {

    @InjectMocks
    private CadastrarContaAppService cadastrarContaAppService;

    @Mock
    private ContaRepository contaRepository;

    @Test
    void executar() {
        CadastrarContaDTO cadastrarContaDTO = new CadastrarContaDTO(
                LocalDateTime.now().plusDays(10),
                LocalDateTime.now(),
                "Conta de água",
                null,
                100.00
        );

        assertDoesNotThrow(() -> this.cadastrarContaAppService.executar(cadastrarContaDTO));

        verify(this.contaRepository).save(any(Conta.class));
    }
}
