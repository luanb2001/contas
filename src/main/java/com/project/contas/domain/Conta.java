package com.project.contas.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.project.contas.domain.enums.SituacaoContaEnum;
import com.project.contas.dto.AtualizarSituacaoContaDTO;
import com.project.contas.dto.CadastrarContaCSV;
import com.project.contas.dto.CadastrarContaDTO;
import com.project.contas.dto.ContaDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CONTA")
public class Conta {
	
	@Id
	private UUID id;
	
	private LocalDateTime dataVencimento;
	
	private LocalDateTime dataPagamento;
	
	private Double valor;
	
	private String descricao;
	
	@Enumerated(EnumType.ORDINAL)
	private SituacaoContaEnum situacao;

    public static Conta cadastrarConta(CadastrarContaDTO cadastrarContaDTO) {
        Conta conta = new Conta();
        conta.id = UUID.randomUUID();
        conta.dataVencimento = cadastrarContaDTO.dataVencimento();
        conta.dataPagamento = cadastrarContaDTO.dataPagamento();
        conta.descricao = cadastrarContaDTO.descricao();
        conta.situacao = cadastrarContaDTO.situacaoContaEnum();
        conta.valor = cadastrarContaDTO.valor();

        return conta;
    }

    public static Conta cadastrarConta(CadastrarContaCSV cadastrarContaCSV) {
        Conta conta = new Conta();
        conta.id = UUID.randomUUID();
        conta.dataVencimento = cadastrarContaCSV.getDataVencimento();
        conta.dataPagamento = cadastrarContaCSV.getDataPagamento();
        conta.descricao = cadastrarContaCSV.getDescricao();
        conta.situacao = cadastrarContaCSV.getSituacaoContaEnum();
        conta.valor = cadastrarContaCSV.getValor();

        return conta;
    }

    public Conta atualizarConta(ContaDTO contaDTO) {
        this.dataVencimento = contaDTO.dataVencimento();
        this.dataPagamento = contaDTO.dataPagamento();
        this.descricao = contaDTO.descricao();
        this.situacao = contaDTO.situacaoContaEnum();
        this.valor = contaDTO.valor();

        return this;
    }

    public Conta atualizarSituacaoConta(AtualizarSituacaoContaDTO atualizarSituacaoContaDTO) {
        this.situacao = atualizarSituacaoContaDTO.situacaoContaEnum();

        if (SituacaoContaEnum.PAGA.equals(atualizarSituacaoContaDTO.situacaoContaEnum())) {
        	this.dataPagamento = LocalDateTime.now();
        }

        return this;
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

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
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

}
