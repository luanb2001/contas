package com.project.contas.adapters.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.contas.domain.dto.LoginDTO;
import com.project.contas.domain.dto.TokenDTO;
import com.project.contas.infrastructure.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private JwtService jwtService;

    @Test
    public void login_comCredenciaisValidas_retornaToken() throws Exception {
        when(jwtService.gerarToken(anyString())).thenReturn("token-de-teste");

        LoginDTO loginDTO = new LoginDTO("desafio", "contas");

        this.mock.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token-de-teste"));
    }

    @Test
    public void login_comCredenciaisInvalidas_retorna400() throws Exception {
        LoginDTO loginDTO = new LoginDTO("usuario-errado", "senha-errada");

        this.mock.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginDTO)))
                .andExpect(status().isBadRequest());
    }
}
