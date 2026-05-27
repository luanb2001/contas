package com.project.contas.adapters.in;

import com.project.contas.application.usecase.BuscarFornecedorPorIdUseCase;
import com.project.contas.application.usecase.CadastrarFornecedorUseCase;
import com.project.contas.domain.dto.CadastrarFornecedorDTO;
import com.project.contas.domain.dto.FornecedorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class FornecedorControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private com.fasterxml.jackson.databind.ObjectMapper mapper;

    @MockBean
    private CadastrarFornecedorUseCase cadastrarFornecedorUseCase;

    @MockBean
    private BuscarFornecedorPorIdUseCase buscarFornecedorPorIdUseCase;

    @Test
    public void cadastrarFornecedor() throws Exception {
        CadastrarFornecedorDTO dto = new CadastrarFornecedorDTO("Fornecedor Teste");

        this.mock.perform(post("/fornecedor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(this.cadastrarFornecedorUseCase, times(1)).executar(any(CadastrarFornecedorDTO.class));
    }

    @Test
    public void buscarFornecedorPorId() throws Exception {
        UUID id = UUID.randomUUID();
        when(this.buscarFornecedorPorIdUseCase.executar(id)).thenReturn(new FornecedorDTO(id, "Fornecedor Teste"));

        this.mock.perform(get("/fornecedor/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(this.buscarFornecedorPorIdUseCase, times(1)).executar(id);
    }
}
