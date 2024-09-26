package com.project.contas.infrastructure.config;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testAccessProtectedUrlWithoutAuthentication() throws Exception {
        this.mockMvc.perform(get("/conta"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void testAccessProtectedUrlWithAuthentication() throws Exception {
    	 UUID contaId = UUID.randomUUID();

        this.mockMvc.perform(get("/conta/" + contaId)
            .with(httpBasic("desafio", "contas")))
            .andExpect(status().isOk())
            .andExpect(authenticated().withUsername("desafio").withRoles("ADMIN"));
    }

    @Test
    public void testUserDetailsService() throws Exception {
        String rawPassword = "contas";
        String encodedPassword = this.passwordEncoder.encode(rawPassword);
        boolean matches = this.passwordEncoder.matches(rawPassword, encodedPassword);
        
        assert(matches);
    }
}
