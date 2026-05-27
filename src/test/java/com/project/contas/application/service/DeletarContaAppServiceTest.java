package com.project.contas.application.service;

import com.project.contas.application.exception.RegraNegocioException;
import com.project.contas.domain.Conta;
import com.project.contas.domain.Fornecedor;
import com.project.contas.domain.enums.SituacaoContaEnum;
import com.project.contas.domain.repository.ContaRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeletarContaAppServiceTest {

    @InjectMocks
    private DeletarContaAppService deletarContaAppService;

    @Mock
    private ContaRepository contaRepository;

    private Conta conta;
    private UUID contaId;

    @BeforeEach
    public void setUp() {
        contaId = UUID.randomUUID();
        conta = new Conta();
        conta.setId(contaId);
        conta.setDataVencimento(LocalDateTime.now().plusDays(30));
        conta.setDescricao("Conta teste");
        conta.setValor(BigDecimal.valueOf(100));
        conta.setSituacao(SituacaoContaEnum.ABERTA);
        conta.setFornecedor(Fornecedor.cadastrarFornecedor("Fornecedor Teste"));
    }

    @Test
    public void executar_quandoContaExiste_deleta() {
        when(contaRepository.findById(contaId)).thenReturn(Optional.of(conta));

        assertDoesNotThrow(() -> deletarContaAppService.executar(contaId));

        verify(contaRepository).deleteById(contaId);
    }

    @Test
    public void executar_quandoContaNaoExiste_lancaExcecao() {
        UUID id = UUID.randomUUID();
        when(contaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RegraNegocioException.class, () -> deletarContaAppService.executar(id));
    }
}
