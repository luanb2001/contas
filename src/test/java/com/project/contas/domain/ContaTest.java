package com.project.contas.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.project.contas.domain.enums.SituacaoContaEnum;
import com.project.contas.dto.AtualizarSituacaoContaDTO;
import com.project.contas.dto.CadastrarContaCSV;
import com.project.contas.dto.CadastrarContaDTO;
import com.project.contas.dto.ContaDTO;


public class ContaTest {

    private Conta conta;

    @BeforeEach
    public void setUp() {
        this.conta = new Conta();
        this.conta.setId(UUID.randomUUID());
        this.conta.setDataVencimento(LocalDateTime.now().plusDays(30));
        this.conta.setDescricao("Teste de conta");
        this.conta.setValor(100.0);
        this.conta.setSituacao(SituacaoContaEnum.ABERTA);
    }

    @Test
    public void testCadastrarContaComCadastrarContaDTO() {
        CadastrarContaDTO cadastrarContaDTO = this.mockCadastrarContaDTO();
        Conta novaConta = Conta.cadastrarConta(cadastrarContaDTO);
        assertNotNull(novaConta);
        assertNotNull(novaConta.getId());
        assertEquals(cadastrarContaDTO.dataVencimento(), novaConta.getDataVencimento());
        assertEquals(cadastrarContaDTO.descricao(), novaConta.getDescricao());
        assertEquals(cadastrarContaDTO.valor(), novaConta.getValor());
        assertEquals(cadastrarContaDTO.situacaoContaEnum(), novaConta.getSituacao());
    }

    @Test
    public void testCadastrarContaComCadastrarContaCSV() {
        CadastrarContaCSV cadastrarContaCSV = this.mockCadastrarContaCSV();
        Conta novaConta = Conta.cadastrarConta(cadastrarContaCSV);
        assertNotNull(novaConta);
        assertNotNull(novaConta.getId());
        assertEquals(cadastrarContaCSV.getDataVencimento(), novaConta.getDataVencimento());
        assertEquals(cadastrarContaCSV.getDescricao(), novaConta.getDescricao());
        assertEquals(cadastrarContaCSV.getValor(), novaConta.getValor());
        assertEquals(cadastrarContaCSV.getSituacaoContaEnum(), novaConta.getSituacao());
    }

    @Test
    public void testAtualizarConta() {
        ContaDTO contaDTO = this.mockContaDTO();
        this.conta.atualizarConta(contaDTO);
        assertEquals(contaDTO.dataVencimento(), this.conta.getDataVencimento());
        assertEquals(contaDTO.descricao(), this.conta.getDescricao());
        assertEquals(contaDTO.valor(), this.conta.getValor());
        assertEquals(contaDTO.situacaoContaEnum(), this.conta.getSituacao());
    }

    @Test
    public void testAtualizarSituacaoConta() {
        AtualizarSituacaoContaDTO atualizarSituacaoContaDTO = new AtualizarSituacaoContaDTO(this.conta.getId(), SituacaoContaEnum.PAGA);
        this.conta.atualizarSituacaoConta(atualizarSituacaoContaDTO);
        assertEquals(atualizarSituacaoContaDTO.situacaoContaEnum(), this.conta.getSituacao());
        assertNotNull(this.conta.getDataPagamento());
    }

    private CadastrarContaDTO mockCadastrarContaDTO() {
        return new CadastrarContaDTO(
            LocalDateTime.now().plusDays(30), 
            LocalDateTime.now().plusDays(15), 
            "Teste DTO", 
            SituacaoContaEnum.ABERTA, 
            100.0);
    }

    private CadastrarContaCSV mockCadastrarContaCSV() {
        CadastrarContaCSV cadastrarContaCSV = new CadastrarContaCSV();
        cadastrarContaCSV.setDataVencimento(LocalDateTime.now().plusDays(30));
        cadastrarContaCSV.setDataPagamento(LocalDateTime.now().plusDays(15));
        cadastrarContaCSV.setDescricao("Teste CSV");
        cadastrarContaCSV.setValor(200.0);
        cadastrarContaCSV.setSituacaoContaEnum(SituacaoContaEnum.ABERTA);
        return cadastrarContaCSV;
    }

    private ContaDTO mockContaDTO() {
        return new ContaDTO(
            this.conta.getId(),
            LocalDateTime.now().plusDays(10),
            LocalDateTime.now().plusDays(5),
            "Teste Atualizado",
            SituacaoContaEnum.PAGA,
            150.0
        );
    }
}
