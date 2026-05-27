package com.project.contas.domain;

import com.project.contas.application.exception.RegraNegocioException;
import com.project.contas.domain.dto.CadastrarContaCSV;
import com.project.contas.domain.enums.SituacaoContaEnum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ContaTest {

    private Conta conta;
    private Fornecedor fornecedor;

    @BeforeEach
    public void setUp() {
        this.fornecedor = Fornecedor.cadastrarFornecedor("Fornecedor Teste");
        this.conta = new Conta();
        this.conta.setId(UUID.randomUUID());
        this.conta.setDataVencimento(LocalDateTime.now().plusDays(30));
        this.conta.setDescricao("Teste de conta");
        this.conta.setValor(BigDecimal.valueOf(100));
        this.conta.setSituacao(SituacaoContaEnum.ABERTA);
        this.conta.setFornecedor(this.fornecedor);
    }

    @Test
    public void cadastrarConta_comParametros_criaContaCorretamente() {
        LocalDateTime dataVencimento = LocalDateTime.now().plusDays(30);
        LocalDateTime dataPagamento = LocalDateTime.now().plusDays(15);
        String descricao = "Teste DTO";
        BigDecimal valor = BigDecimal.valueOf(100);

        Conta novaConta = Conta.cadastrarConta(dataVencimento, dataPagamento, descricao,
                SituacaoContaEnum.ABERTA, valor, fornecedor);

        assertNotNull(novaConta);
        assertNotNull(novaConta.getId());
        assertEquals(dataVencimento, novaConta.getDataVencimento());
        assertEquals(descricao, novaConta.getDescricao());
        assertEquals(valor, novaConta.getValor());
        assertEquals(SituacaoContaEnum.ABERTA, novaConta.getSituacao());
        assertEquals(fornecedor, novaConta.getFornecedor());
    }

    @Test
    public void cadastrarConta_comCSV_criaContaCorretamente() {
        CadastrarContaCSV cadastrarContaCSV = this.mockCadastrarContaCSV();
        Conta novaConta = Conta.cadastrarConta(cadastrarContaCSV, fornecedor);
        assertNotNull(novaConta);
        assertNotNull(novaConta.getId());
        assertEquals(cadastrarContaCSV.getDataVencimento(), novaConta.getDataVencimento());
        assertEquals(cadastrarContaCSV.getDescricao(), novaConta.getDescricao());
        assertEquals(cadastrarContaCSV.getValor(), novaConta.getValor());
        assertEquals(cadastrarContaCSV.getSituacaoContaEnum(), novaConta.getSituacao());
    }

    @Test
    public void atualizar_quandoContaAberta_atualizaDados() {
        LocalDateTime dataVencimento = LocalDateTime.now().plusDays(10);
        BigDecimal valor = BigDecimal.valueOf(150);
        this.conta.atualizar(dataVencimento, LocalDateTime.now().plusDays(5),
                "Teste Atualizado", SituacaoContaEnum.PAGA, valor, fornecedor);
        assertEquals(dataVencimento, this.conta.getDataVencimento());
        assertEquals("Teste Atualizado", this.conta.getDescricao());
        assertEquals(valor, this.conta.getValor());
    }

    @Test
    public void pagar_quandoContaAberta_marcaComoPaga() {
        this.conta.pagar();
        assertEquals(SituacaoContaEnum.PAGA, this.conta.getSituacao());
        assertNotNull(this.conta.getDataPagamento());
    }

    @Test
    public void pagar_quandoContaJaPaga_lancaExcecao() {
        this.conta.setSituacao(SituacaoContaEnum.PAGA);
        assertThrows(RegraNegocioException.class, () -> this.conta.pagar());
    }

    @Test
    public void pagar_quandoContaCancelada_lancaExcecao() {
        this.conta.setSituacao(SituacaoContaEnum.CANCELADA);
        assertThrows(RegraNegocioException.class, () -> this.conta.pagar());
    }

    @Test
    public void cancelar_quandoContaAberta_marcaComoCancelada() {
        this.conta.cancelar();
        assertEquals(SituacaoContaEnum.CANCELADA, this.conta.getSituacao());
    }

    @Test
    public void cancelar_quandoContaPaga_lancaExcecao() {
        this.conta.setSituacao(SituacaoContaEnum.PAGA);
        assertThrows(RegraNegocioException.class, () -> this.conta.cancelar());
    }

    @Test
    public void atualizar_quandoContaPaga_lancaExcecao() {
        this.conta.setSituacao(SituacaoContaEnum.PAGA);
        assertThrows(RegraNegocioException.class, () ->
                this.conta.atualizar(LocalDateTime.now().plusDays(10), null, "desc",
                        SituacaoContaEnum.ABERTA, BigDecimal.valueOf(50), fornecedor));
    }

    @Test
    public void atualizar_quandoContaCancelada_lancaExcecao() {
        this.conta.setSituacao(SituacaoContaEnum.CANCELADA);
        assertThrows(RegraNegocioException.class, () ->
                this.conta.atualizar(LocalDateTime.now().plusDays(10), null, "desc",
                        SituacaoContaEnum.ABERTA, BigDecimal.valueOf(50), fornecedor));
    }

    @Test
    public void abrir_quandoContaVencida_retornaAberta() {
        this.conta.setSituacao(SituacaoContaEnum.VENCIDA);
        this.conta.abrir();
        assertEquals(SituacaoContaEnum.ABERTA, this.conta.getSituacao());
    }

    @Test
    public void abrir_quandoContaPaga_lancaExcecao() {
        this.conta.setSituacao(SituacaoContaEnum.PAGA);
        assertThrows(RegraNegocioException.class, () -> this.conta.abrir());
    }

    @Test
    public void vencer_quandoContaAberta_retornaVencida() {
        this.conta.vencer();
        assertEquals(SituacaoContaEnum.VENCIDA, this.conta.getSituacao());
    }

    @Test
    public void vencer_quandoContaPaga_lancaExcecao() {
        this.conta.setSituacao(SituacaoContaEnum.PAGA);
        assertThrows(RegraNegocioException.class, () -> this.conta.vencer());
    }

    private CadastrarContaCSV mockCadastrarContaCSV() {
        CadastrarContaCSV cadastrarContaCSV = new CadastrarContaCSV();
        cadastrarContaCSV.setDataVencimento(LocalDateTime.now().plusDays(30));
        cadastrarContaCSV.setDataPagamento(LocalDateTime.now().plusDays(15));
        cadastrarContaCSV.setDescricao("Teste CSV");
        cadastrarContaCSV.setValor(BigDecimal.valueOf(200));
        cadastrarContaCSV.setSituacaoContaEnum(SituacaoContaEnum.ABERTA);
        cadastrarContaCSV.setFornecedorId(fornecedor.getId().toString());
        return cadastrarContaCSV;
    }
}


