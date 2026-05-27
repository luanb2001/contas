package com.project.contas.adapters.in;

import com.project.contas.application.usecase.BuscarImportacaoContaUseCase;
import com.project.contas.domain.dto.ImportacaoContaDTO;
import com.project.contas.domain.enums.StatusImportacaoEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ImportacaoContaControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private BuscarImportacaoContaUseCase buscarImportacaoContaUseCase;

    @Test
    public void buscarImportacao() throws Exception {
        UUID id = UUID.randomUUID();
        ImportacaoContaDTO dto = new ImportacaoContaDTO(id, StatusImportacaoEnum.FINALIZADA, LocalDateTime.now(), 1, 1, 0);
        when(buscarImportacaoContaUseCase.executar(id)).thenReturn(dto);

        this.mock.perform(get("/importacao/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
