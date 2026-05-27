package com.project.contas.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.contas.domain.dto.LoginDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAccessProtectedUrlWithoutAuthentication() throws Exception {
        this.mockMvc.perform(get("/conta/listar-contas"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLoginReturnsToken() throws Exception {
        LoginDTO loginDTO = new LoginDTO("desafio", "contas");

        MvcResult result = this.mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        assert body.contains("token");
    }

    @Test
    public void testSwaggerPermitidoSemAutenticacao() throws Exception {
        this.mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk());
    }
}
