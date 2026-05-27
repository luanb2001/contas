package com.project.contas.application.service;

import com.project.contas.application.exception.RegraNegocioException;
import com.project.contas.domain.Conta;
import com.project.contas.domain.Fornecedor;
import com.project.contas.domain.enums.SituacaoContaEnum;
import com.project.contas.domain.repository.ContaRepository;
import com.project.contas.domain.dto.AtualizarSituacaoContaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AtualizarSituacaoContaAppServiceTest {

    @InjectMocks
    private AtualizarSituacaoContaAppService atualizarSituacaoContaAppService;

    @Mock
    private ContaRepository contaRepository;

    private Conta conta;
    private UUID contaId;

    @BeforeEach
    public void setUp() {
        contaId = UUID.randomUUID();
        Fornecedor fornecedor = Fornecedor.cadastrarFornecedor("Fornecedor Teste");
        conta = new Conta();
        conta.setId(contaId);
        conta.setDataVencimento(LocalDateTime.now().plusDays(30));
        conta.setDescricao("Conta teste");
        conta.setValor(BigDecimal.valueOf(100));
        conta.setSituacao(SituacaoContaEnum.ABERTA);
        conta.setFornecedor(fornecedor);
    }

    @Test
    public void executar_pagar_contaAberta_sucesso() {
        when(contaRepository.findById(contaId)).thenReturn(Optional.of(conta));
        assertDoesNotThrow(() -> atualizarSituacaoContaAppService.executar(
                new AtualizarSituacaoContaDTO(contaId, SituacaoContaEnum.PAGA)));
    }

    @Test
    public void executar_cancelar_contaAberta_sucesso() {
        when(contaRepository.findById(contaId)).thenReturn(Optional.of(conta));
        assertDoesNotThrow(() -> atualizarSituacaoContaAppService.executar(
                new AtualizarSituacaoContaDTO(contaId, SituacaoContaEnum.CANCELADA)));
    }

    @Test
    public void executar_vencer_contaAberta_sucesso() {
        when(contaRepository.findById(contaId)).thenReturn(Optional.of(conta));
        assertDoesNotThrow(() -> atualizarSituacaoContaAppService.executar(
                new AtualizarSituacaoContaDTO(contaId, SituacaoContaEnum.VENCIDA)));
    }

    @Test
    public void executar_abrir_contaVencida_sucesso() {
        conta.setSituacao(SituacaoContaEnum.VENCIDA);
        when(contaRepository.findById(contaId)).thenReturn(Optional.of(conta));
        assertDoesNotThrow(() -> atualizarSituacaoContaAppService.executar(
                new AtualizarSituacaoContaDTO(contaId, SituacaoContaEnum.ABERTA)));
    }

    @Test
    public void executar_pagar_contaJaPaga_lancaExcecao() {
        conta.setSituacao(SituacaoContaEnum.PAGA);
        when(contaRepository.findById(contaId)).thenReturn(Optional.of(conta));
        assertThrows(RegraNegocioException.class, () -> atualizarSituacaoContaAppService.executar(
                new AtualizarSituacaoContaDTO(contaId, SituacaoContaEnum.PAGA)));
    }

    @Test
    public void executar_abrir_contaPaga_lancaExcecao() {
        conta.setSituacao(SituacaoContaEnum.PAGA);
        when(contaRepository.findById(contaId)).thenReturn(Optional.of(conta));
        assertThrows(RegraNegocioException.class, () -> atualizarSituacaoContaAppService.executar(
                new AtualizarSituacaoContaDTO(contaId, SituacaoContaEnum.ABERTA)));
    }

    @Test
    public void executar_cancelar_contaPaga_lancaExcecao() {
        conta.setSituacao(SituacaoContaEnum.PAGA);
        when(contaRepository.findById(contaId)).thenReturn(Optional.of(conta));
        assertThrows(RegraNegocioException.class, () -> atualizarSituacaoContaAppService.executar(
                new AtualizarSituacaoContaDTO(contaId, SituacaoContaEnum.CANCELADA)));
    }
}
