package com.project.contas.application.service;

import com.project.contas.adapters.out.messaging.ImportacaoContaProducer;
import com.project.contas.domain.ImportacaoConta;
import com.project.contas.domain.dto.ImportarContasCSVMessage;
import com.project.contas.domain.repository.ImportacaoContaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImportarContasCSVAppServiceTest {

    @InjectMocks
    private ImportarContasCSVAppService importarContasCSVAppService;

    @Mock
    private ImportacaoContaRepository importacaoContaRepository;

    @Mock
    private ImportacaoContaProducer importacaoContaProducer;

    @Test
    public void executar_criaImportacaoEPublicaMensagem() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "contas.csv", "text/csv",
                "dataVencimento,dataPagamento,descricao,situacaoContaEnum,valor,fornecedorId\n".getBytes());

        when(importacaoContaRepository.save(any(ImportacaoConta.class))).thenAnswer(inv -> inv.getArgument(0));

        UUID protocolo = importarContasCSVAppService.executar(file);

        assertNotNull(protocolo);
        verify(importacaoContaRepository).save(any(ImportacaoConta.class));
        verify(importacaoContaProducer).enviarMensagem(any(ImportarContasCSVMessage.class));
    }
}
