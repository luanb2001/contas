package com.project.contas.adapters.in;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.UUID;

import com.project.contas.application.usecase.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.contas.domain.enums.SituacaoContaEnum;
import com.project.contas.domain.dto.AtualizarSituacaoContaDTO;
import com.project.contas.domain.dto.CadastrarContaDTO;
import com.project.contas.domain.dto.ContaDTO;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ContaControllerTest {

    public static final LocalDateTime DATA_VENCIMENTO = LocalDateTime.now().plusDays(10);
    public static final LocalDateTime DATA_PAGAMENTO = LocalDateTime.now();
    public static final String DESCRICAO = "Conta de internet";
    public static final SituacaoContaEnum SITUACAO_CONTA = SituacaoContaEnum.ABERTA;
    public static final Double VALOR = 170.00;

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CadastrarContaUseCase cadastrarContaUseCase;

    @MockBean
    private AtualizarContaUseCase atualizarContaUseCase;

    @MockBean
    private AtualizarSituacaoContaUseCase atualizarSituacaoContaUseCase;

    @MockBean
    private BuscarContaPorIdUseCase buscarContaPorIdUseCase;

    @MockBean
    private ListarContasUseCase listarContasUseCase;

    @MockBean
    private CarregarValorPagoUseCase carregarValorPagoUseCase;

    @MockBean
    private ImportarContasCSVUseCase importarContasUseCase;

    @Test
    void cadastrarConta() throws Exception {
        CadastrarContaDTO cadastrarContaDTO = new CadastrarContaDTO(
            DATA_VENCIMENTO, DATA_PAGAMENTO, DESCRICAO, SITUACAO_CONTA, VALOR
        );

        String dto = this.mapper.writeValueAsString(cadastrarContaDTO);

        this.mock.perform(post("/conta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dto))
                .andExpect(status().isOk())
                .andReturn();

        verify(this.cadastrarContaUseCase, times(1)).executar(any(CadastrarContaDTO.class));
    }

    @Test
    void atualizarConta() throws Exception {
        ContaDTO atualizarContaDTO = new ContaDTO(
                UUID.randomUUID(), DATA_VENCIMENTO, DATA_PAGAMENTO, DESCRICAO, SITUACAO_CONTA, VALOR
        );

        String dto = this.mapper.writeValueAsString(atualizarContaDTO);

        this.mock.perform(put("/conta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dto))
                .andExpect(status().isOk())
                .andReturn();

        verify(this.atualizarContaUseCase, times(1)).executar(any(ContaDTO.class));
    }

    @Test
    void atualizarSituacaoConta() throws Exception {
        AtualizarSituacaoContaDTO atualizarSituacaoDTO = new AtualizarSituacaoContaDTO(
                UUID.randomUUID(), SituacaoContaEnum.PAGA
        );

        String dto = this.mapper.writeValueAsString(atualizarSituacaoDTO);

        this.mock.perform(patch("/conta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dto))
                .andExpect(status().isOk())
                .andReturn();

        verify(this.atualizarSituacaoContaUseCase, times(1)).executar(any(AtualizarSituacaoContaDTO.class));
    }

    @Test
    void buscarContaPorId() throws Exception {
        UUID id = UUID.randomUUID();
        this.mock.perform(get("/conta/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(this.buscarContaPorIdUseCase, times(1)).executar(id);
    }

    @Test
    void listarContas() throws Exception {
        this.mock.perform(get("/conta/listar-contas?descricao=testeListarContas&data-vencimento-final=2024-09-20T12:00:00&data-vencimento-inicial=2024-09-20T12:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(this.listarContasUseCase, times(1)).executar(any(), any(), any(), any());
    }

    @Test
    void carregarValorPagoPorPeriodo() throws Exception {
        this.mock.perform(get("/conta/carregar-valor-pago?data-inicial=2024-09-20T12:00:00&data-final=2024-09-20T12:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(this.carregarValorPagoUseCase, times(1)).executar(any(), any());
    }

    @Test
    void importarContas() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "csv-exemplo-gerar-contas.csv",
                "application/x-csv",
                new ClassPathResource("gerar-contas.csv").getInputStream());

        this.mock.perform(MockMvcRequestBuilders.multipart("/conta/importar-conta")
                        .file(mockMultipartFile))
                .andExpect(status().isOk());

        verify(this.importarContasUseCase, times(1)).executar(any());
    }
}
