package com.project.contas.adapters.in.messaging;

import com.opencsv.bean.CsvToBeanBuilder;
import com.project.contas.domain.Conta;
import com.project.contas.domain.Fornecedor;
import com.project.contas.domain.ImportacaoConta;
import com.project.contas.domain.dto.CadastrarContaCSV;
import com.project.contas.domain.dto.ImportarContasCSVMessage;
import com.project.contas.domain.repository.ContaRepository;
import com.project.contas.domain.repository.FornecedorRepository;
import com.project.contas.domain.repository.ImportacaoContaRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static com.project.contas.infrastructure.config.RabbitMQConfig.IMPORTACAO_CONTA_QUEUE;

@Component
public class ImportacaoContaConsumer {

    private final ImportacaoContaRepository importacaoContaRepository;
    private final ContaRepository contaRepository;
    private final FornecedorRepository fornecedorRepository;

    public ImportacaoContaConsumer(ImportacaoContaRepository importacaoContaRepository,
                                   ContaRepository contaRepository,
                                   FornecedorRepository fornecedorRepository) {
        this.importacaoContaRepository = importacaoContaRepository;
        this.contaRepository = contaRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    @RabbitListener(queues = IMPORTACAO_CONTA_QUEUE)
    public void processarImportacao(ImportarContasCSVMessage mensagem) {
        ImportacaoConta importacao = importacaoContaRepository.findById(mensagem.importacaoId())
                .orElseThrow(() -> new RuntimeException("Importação não encontrada: " + mensagem.importacaoId()));

        importacao.iniciarProcessamento();
        importacaoContaRepository.save(importacao);

        List<CadastrarContaCSV> linhas;
        try {
            byte[] bytes = Base64.getDecoder().decode(mensagem.conteudoBase64());
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes)))) {
                linhas = new CsvToBeanBuilder<CadastrarContaCSV>(reader)
                        .withType(CadastrarContaCSV.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build()
                        .parse();
            }
        } catch (Exception e) {
            importacao.erro("Erro ao parsear CSV: " + e.getMessage());
            importacaoContaRepository.save(importacao);
            return;
        }

        int processados = 0;
        int falhas = 0;

        for (CadastrarContaCSV csv : linhas) {
            try {
                UUID fornecedorUUID = UUID.fromString(csv.getFornecedorId());
                Fornecedor fornecedor = fornecedorRepository.findById(fornecedorUUID)
                        .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado: " + fornecedorUUID));
                contaRepository.save(Conta.cadastrarConta(csv, fornecedor));
                processados++;
            } catch (Exception e) {
                falhas++;
            }
        }

        importacao.finalizar(linhas.size(), processados, falhas);
        importacaoContaRepository.save(importacao);
    }
}
