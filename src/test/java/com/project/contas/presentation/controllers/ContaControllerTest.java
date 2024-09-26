package com.project.contas.presentation.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.contas.domain.enums.SituacaoContaEnum;
import com.project.contas.dto.AtualizarSituacaoContaDTO;
import com.project.contas.dto.CadastrarContaDTO;
import com.project.contas.dto.ContaDTO;

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

    @Test
    void cadastrarConta() throws Exception {
        CadastrarContaDTO cadastrarContaDTO = new CadastrarContaDTO(
            DATA_VENCIMENTO, DATA_PAGAMENTO, DESCRICAO, SITUACAO_CONTA, VALOR
        );

        String dto = this.mapper.writeValueAsString(cadastrarContaDTO);

        mock.perform(post("/conta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dto))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void atualizarConta() throws Exception {
        ContaDTO atualizarContaDTO = new ContaDTO(
                UUID.randomUUID(), DATA_VENCIMENTO, DATA_PAGAMENTO, DESCRICAO, SITUACAO_CONTA, VALOR
        );

        String dto = this.mapper.writeValueAsString(atualizarContaDTO);

        mock.perform(put("/conta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dto))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void atualizarSituacaoConta() throws Exception {
        AtualizarSituacaoContaDTO atualizarSituacaoDTO = new AtualizarSituacaoContaDTO(
                UUID.randomUUID(), SituacaoContaEnum.PAGA
        );

        String dto = this.mapper.writeValueAsString(atualizarSituacaoDTO);

        mock.perform(patch("/conta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dto))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void buscarContaPorId() throws Exception {
        UUID id = UUID.randomUUID();
        mock.perform(get("/conta/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void listarContas() throws Exception {
        mock.perform(get("/conta/listar-contas?descricao=testeListarContas&data-vencimento-final=2024-09-20T12:00:00&data-vencimento-inicial=2024-09-20T12:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void carregarValorPagoPorPeriodo() throws Exception {
        mock.perform(get("/conta/carregar-valor-pago?data-inicial=2024-09-20T12:00:00&data-final=2024-09-20T12:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void importarContas() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "csv-exemplo-gerar-contas.csv",
                "application/x-csv",
                new ClassPathResource("gerar-contas.csv").getInputStream());

        mock.perform(MockMvcRequestBuilders.multipart("/conta/importar-conta")
                        .file(mockMultipartFile))
                .andExpect(status().isOk());
    }
}
