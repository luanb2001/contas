package com.project.contas.application.service;

import com.project.contas.domain.repository.ContaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class BuscarContaPorIdAppServiceTest {

    @InjectMocks
    private BuscarContaPorIdAppService buscarContaPorIdAppService;

    @Mock
    private ContaRepository contaRepository;

    @Test
    void executar() {
        assertDoesNotThrow(() -> this.buscarContaPorIdAppService.executar(UUID.randomUUID()));
    }
}
