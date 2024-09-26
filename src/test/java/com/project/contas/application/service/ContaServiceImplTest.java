package com.project.contas.application.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.project.contas.domain.Conta;
import com.project.contas.domain.enums.SituacaoContaEnum;
import com.project.contas.domain.repository.ContaRepository;
import com.project.contas.dto.AtualizarSituacaoContaDTO;
import com.project.contas.dto.CadastrarContaDTO;
import com.project.contas.dto.ContaDTO;


public class ContaServiceImplTest {

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ContaServiceImpl contaServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarConta() {
        CadastrarContaDTO cadastrarContaDTO = mock(CadastrarContaDTO.class);
        when(cadastrarContaDTO.dataVencimento()).thenReturn(LocalDateTime.now());
        when(cadastrarContaDTO.dataPagamento()).thenReturn(LocalDateTime.now());
        when(cadastrarContaDTO.descricao()).thenReturn("Descrição Teste");
        when(cadastrarContaDTO.valor()).thenReturn(100.0);

        this.contaServiceImpl.cadastrarConta(cadastrarContaDTO);

        verify(this.contaRepository, times(1)).save(any(Conta.class));
    }

    @Test
    public void testAtualizarConta() {
        ContaDTO contaDTO = mock(ContaDTO.class);
        when(contaDTO.id()).thenReturn(UUID.randomUUID());

        Conta conta = mock(Conta.class);
        when(this.contaRepository.findById(any(UUID.class))).thenReturn(Optional.of(conta));
        when(conta.atualizarConta(contaDTO)).thenReturn(conta);

        this.contaServiceImpl.atualizarConta(contaDTO);

        verify(this.contaRepository, times(1)).save(any(Conta.class));
    }

    @Test
    public void testAtualizarSituacaoConta() {
        AtualizarSituacaoContaDTO atualizarSituacaoContaDTO = mock(AtualizarSituacaoContaDTO.class);
        UUID contaId = UUID.randomUUID();
        when(atualizarSituacaoContaDTO.id()).thenReturn(contaId);
        when(atualizarSituacaoContaDTO.situacaoContaEnum()).thenReturn(SituacaoContaEnum.ABERTA);

        Conta conta = new Conta();
        conta.setSituacao(SituacaoContaEnum.CANCELADA);
        when(this.contaRepository.findById(contaId)).thenReturn(Optional.of(conta));

        this.contaServiceImpl.atualizarSituacaoConta(atualizarSituacaoContaDTO);

        verify(this.contaRepository, times(1)).save(conta);
    }


    @Test
    public void testBuscarContaPorId() {
        UUID id = UUID.randomUUID();
        ContaDTO contaDTO = mock(ContaDTO.class);
        when(this.contaRepository.buscarContaDTOPorId(id)).thenReturn(contaDTO);

        ContaDTO result = this.contaServiceImpl.buscarContaPorId(id);

        verify(this.contaRepository, times(1)).buscarContaDTOPorId(id);
    }

    @Test
    public void testListarContasPorFiltro() {
        LocalDateTime dataInicial = LocalDateTime.now();
        LocalDateTime dataFinal = LocalDateTime.now();
        Pageable pageable = PageRequest.of(0, 10);
        when(this.contaRepository.listarContasPorFiltro(any(), any(), anyString(), any(Pageable.class)))
                .thenReturn(Collections.emptyList());

        this.contaServiceImpl.listarContasPorFiltro(dataInicial, dataFinal, "descricao", pageable);

        verify(this.contaRepository, times(1))
                .listarContasPorFiltro(any(LocalDateTime.class), any(LocalDateTime.class), anyString(), any(Pageable.class));
    }

    @Test
    public void testCarregarValorPagoPorPeriodo() {
        LocalDateTime dataInicial = LocalDateTime.now();
        LocalDateTime dataFinal = LocalDateTime.now();
        when(this.contaRepository.carregarValorPagoPorPeriodo(dataInicial, dataFinal)).thenReturn(1000.0);

        Double valor = this.contaServiceImpl.carregarValorPagoPorPeriodo(dataInicial, dataFinal);

        verify(this.contaRepository, times(1)).carregarValorPagoPorPeriodo(dataInicial, dataFinal);
    }

}
