package com.project.contas.application.service;

import com.project.contas.domain.Conta;
import com.project.contas.domain.repository.ContaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImportarContasCSVAppServiceTest {

    @InjectMocks
    private ImportarContasCSVAppService importarContasCSVAppService;

    @Mock
    private ContaRepository contaRepository;

    @Test
    void executarDeveImportarComSucesso() throws Exception {
        String csvContent = "descricao,valor\nConta Teste,100.00";
        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes());
        MultipartFile multipartFile = new MockMultipartFile("file.csv", "file.csv", "text/csv", inputStream);

        Boolean resultado = this.importarContasCSVAppService.executar(multipartFile);
        assertTrue(resultado);

        verify(this.contaRepository).save(any(Conta.class));
    }

    @Test
    void executarDeveRetornarFalseQuandoCSVInvalido() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("".getBytes());
        MultipartFile multipartFile = new MockMultipartFile("file.csv", inputStream);

        Boolean resultado = this.importarContasCSVAppService.executar(multipartFile);
        assertFalse(resultado);
    }
}
