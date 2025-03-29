package com.project.contas.application.service;

import com.project.contas.domain.repository.ContaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class ListarContasAppServiceTest {

    @InjectMocks
    private ListarContasAppService listarContasAppService;

    @Mock
    private ContaRepository contaRepository;

    @Test
    public void executar() {
        assertDoesNotThrow(() -> this.listarContasAppService.executar(
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Conta de água",
                PageRequest.of(0, 10)));
    }
}
