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
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.project.contas.infrastructure.config.RabbitMQConfig.IMPORTACAO_CONTA_QUEUE;

@Component
public class ImportacaoContaConsumer {

    private final ImportacaoContaRepository importacaoContaRepository;
    private final ContaRepository contaRepository;
    private final FornecedorRepository fornecedorRepository;
    private final Validator validator;

    public ImportacaoContaConsumer(ImportacaoContaRepository importacaoContaRepository,
                                   ContaRepository contaRepository,
                                   FornecedorRepository fornecedorRepository,
                                   Validator validator) {
        this.importacaoContaRepository = importacaoContaRepository;
        this.contaRepository = contaRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.validator = validator;
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

        Map<UUID, Fornecedor> fornecedorCache = carregarFornecedores(linhas);

        int processados = 0;
        int falhas = 0;
        List<String> detalhes = new ArrayList<>();

        for (int i = 0; i < linhas.size(); i++) {
            Optional<String> erro = processarLinha(i + 1, linhas.get(i), fornecedorCache);
            if (erro.isPresent()) {
                detalhes.add(erro.get());
                falhas++;
            } else {
                processados++;
            }
        }

        importacao.finalizar(linhas.size(), processados, falhas, String.join("; ", detalhes));
        importacaoContaRepository.save(importacao);
    }

    private Optional<String> processarLinha(int numeroLinha, CadastrarContaCSV csv, Map<UUID, Fornecedor> fornecedorCache) {
        try {
            Set<ConstraintViolation<CadastrarContaCSV>> violations = validator.validate(csv);
            if (!violations.isEmpty()) {
                return Optional.of("Linha " + numeroLinha + ": " + formatarViolacoes(violations));
            }

            UUID fornecedorUUID = UUID.fromString(csv.getFornecedorId());
            Fornecedor fornecedor = fornecedorCache.get(fornecedorUUID);
            if (fornecedor == null) {
                return Optional.of("Linha " + numeroLinha + ": Fornecedor não encontrado: " + fornecedorUUID);
            }

            contaRepository.save(Conta.cadastrarConta(csv, fornecedor));
            return Optional.empty();
        } catch (Exception e) {
            return Optional.of("Linha " + numeroLinha + ": " + e.getMessage());
        }
    }

    private String formatarViolacoes(Set<ConstraintViolation<CadastrarContaCSV>> violations) {
        return violations.stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining(", "));
    }

    private Map<UUID, Fornecedor> carregarFornecedores(List<CadastrarContaCSV> linhas) {
        Collection<UUID> ids = linhas.stream()
                .map(csv -> {
                    try {
                        return UUID.fromString(csv.getFornecedorId());
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return fornecedorRepository.findAllByIdIn(ids).stream()
                .collect(Collectors.toMap(Fornecedor::getId, f -> f));
    }
}