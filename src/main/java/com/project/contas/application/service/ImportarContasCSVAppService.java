package com.project.contas.application.service;

import com.project.contas.application.exception.RegraNegocioException;
import com.project.contas.application.usecase.ImportarContasCSVUseCase;
import com.project.contas.adapters.out.messaging.ImportacaoContaProducer;
import com.project.contas.domain.ImportacaoConta;
import com.project.contas.domain.dto.ImportarContasCSVMessage;
import com.project.contas.domain.repository.ImportacaoContaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.UUID;

@Service
@Transactional
public class ImportarContasCSVAppService implements ImportarContasCSVUseCase {

    private final ImportacaoContaRepository importacaoContaRepository;
    private final ImportacaoContaProducer importacaoContaProducer;

    public ImportarContasCSVAppService(ImportacaoContaRepository importacaoContaRepository,
                                       ImportacaoContaProducer importacaoContaProducer) {
        this.importacaoContaRepository = importacaoContaRepository;
        this.importacaoContaProducer = importacaoContaProducer;
    }

    @Override
    public UUID executar(MultipartFile file) {
        try {
            ImportacaoConta importacao = ImportacaoConta.iniciar();
            importacaoContaRepository.save(importacao);

            String conteudoBase64 = Base64.getEncoder().encodeToString(file.getBytes());
            ImportarContasCSVMessage mensagem = new ImportarContasCSVMessage(
                    importacao.getId(),
                    file.getOriginalFilename(),
                    conteudoBase64
            );

            importacaoContaProducer.enviarMensagem(mensagem);
            return importacao.getId();
        } catch (Exception e) {
            throw new RegraNegocioException("Erro ao iniciar importação: " + e.getMessage());
        }
    }
}
