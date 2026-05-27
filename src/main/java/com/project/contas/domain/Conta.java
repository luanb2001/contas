package com.project.contas.domain;

import com.project.contas.application.exception.RegraNegocioException;
import com.project.contas.domain.dto.CadastrarContaCSV;
import com.project.contas.domain.enums.SituacaoContaEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "CONTA")
public class Conta {

    @Id
    private UUID id;

    private LocalDateTime dataVencimento;

    private LocalDateTime dataPagamento;

    private BigDecimal valor;

    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoContaEnum situacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    public static Conta cadastrarConta(LocalDateTime dataVencimento, LocalDateTime dataPagamento,
                                       String descricao, SituacaoContaEnum situacao,
                                       BigDecimal valor, Fornecedor fornecedor) {
        Conta conta = new Conta();
        conta.id = UUID.randomUUID();
        conta.dataVencimento = dataVencimento;
        conta.dataPagamento = dataPagamento;
        conta.descricao = descricao;
        conta.situacao = situacao;
        conta.valor = valor;
        conta.fornecedor = fornecedor;
        return conta;
    }

    public static Conta cadastrarConta(CadastrarContaCSV csv, Fornecedor fornecedor) {
        Conta conta = new Conta();
        conta.id = UUID.randomUUID();
        conta.dataVencimento = csv.getDataVencimento();
        conta.dataPagamento = csv.getDataPagamento();
        conta.descricao = csv.getDescricao();
        conta.situacao = csv.getSituacaoContaEnum();
        conta.valor = csv.getValor();
        conta.fornecedor = fornecedor;
        return conta;
    }

    public Conta atualizar(LocalDateTime dataVencimento, LocalDateTime dataPagamento,
                           String descricao, SituacaoContaEnum situacao,
                           BigDecimal valor, Fornecedor fornecedor) {
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.descricao = descricao;
        this.situacao = situacao;
        this.valor = valor;
        this.fornecedor = fornecedor;
        return this;
    }

    public void pagar() {
        if (SituacaoContaEnum.PAGA.equals(this.situacao)) {
            throw new RegraNegocioException("Conta já está paga");
        }
        if (SituacaoContaEnum.CANCELADA.equals(this.situacao)) {
            throw new RegraNegocioException("Conta cancelada não pode ser paga");
        }
        this.situacao = SituacaoContaEnum.PAGA;
        this.dataPagamento = LocalDateTime.now();
    }

    public void cancelar() {
        if (SituacaoContaEnum.PAGA.equals(this.situacao)) {
            throw new RegraNegocioException("Conta paga não pode ser cancelada");
        }
        this.situacao = SituacaoContaEnum.CANCELADA;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public SituacaoContaEnum getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoContaEnum situacao) {
        this.situacao = situacao;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
}
