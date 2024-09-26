package com.project.contas.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.project.contas.domain.enums.SituacaoContaEnum;

import java.time.LocalDateTime;

public class CadastrarContaCSV {

    @CsvDate(value = "yyyy-MM-dd HH:mm:ss")
    @CsvBindByName(column = "dataVencimento")
    private LocalDateTime dataVencimento;

    @CsvDate(value = "yyyy-MM-dd HH:mm:ss")
    @CsvBindByName(column = "dataPagamento")
    private LocalDateTime dataPagamento;

    @CsvBindByName(column = "descricao")
    private String descricao;

    @CsvBindByName(column = "situacaoContaEnum")
    private SituacaoContaEnum situacaoContaEnum;

    @CsvBindByName(column = "valor")
    private Double valor;

    public LocalDateTime getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public SituacaoContaEnum getSituacaoContaEnum() {
        return situacaoContaEnum;
    }

    public void setSituacaoContaEnum(SituacaoContaEnum situacaoContaEnum) {
        this.situacaoContaEnum = situacaoContaEnum;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}
