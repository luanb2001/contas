package com.project.contas.application.service;

import com.project.contas.domain.repository.ContaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class CarregarValorPagoAppServiceTest {

    @InjectMocks
    private CarregarValorPagoAppService carregarValorPagoAppService;

    @Mock
    private ContaRepository contaRepository;

    @Test
    public void executar() {
        assertDoesNotThrow(() -> this.carregarValorPagoAppService.executar(LocalDateTime.now(), LocalDateTime.now().plusDays(10)));
    }
}
