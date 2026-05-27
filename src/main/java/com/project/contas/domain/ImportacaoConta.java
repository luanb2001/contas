package com.project.contas.domain;

import com.project.contas.domain.enums.StatusImportacaoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "IMPORTACAO_CONTA")
public class ImportacaoConta {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusImportacaoEnum status;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataInicioProcessamento;
    private LocalDateTime dataFimProcessamento;
    private Integer totalRegistros;
    private Integer totalProcessados;
    private Integer totalFalhas;
    private String mensagemErro;

    protected ImportacaoConta() {
    }

    public static ImportacaoConta iniciar() {
        ImportacaoConta importacao = new ImportacaoConta();
        importacao.id = UUID.randomUUID();
        importacao.status = StatusImportacaoEnum.PENDENTE;
        importacao.dataCriacao = LocalDateTime.now();
        return importacao;
    }

    public void iniciarProcessamento() {
        this.status = StatusImportacaoEnum.PROCESSANDO;
        this.dataInicioProcessamento = LocalDateTime.now();
    }

    public void finalizar(int totalRegistros, int totalProcessados, int totalFalhas) {
        this.totalRegistros = totalRegistros;
        this.totalProcessados = totalProcessados;
        this.totalFalhas = totalFalhas;
        this.dataFimProcessamento = LocalDateTime.now();
        this.status = totalFalhas > 0
                ? StatusImportacaoEnum.FINALIZADA_COM_ERROS
                : StatusImportacaoEnum.FINALIZADA;
    }

    public void erro(String mensagem) {
        this.status = StatusImportacaoEnum.ERRO;
        this.mensagemErro = mensagem;
        this.dataFimProcessamento = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public StatusImportacaoEnum getStatus() {
        return status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataInicioProcessamento() {
        return dataInicioProcessamento;
    }

    public LocalDateTime getDataFimProcessamento() {
        return dataFimProcessamento;
    }

    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    public Integer getTotalProcessados() {
        return totalProcessados;
    }

    public Integer getTotalFalhas() {
        return totalFalhas;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }
}
