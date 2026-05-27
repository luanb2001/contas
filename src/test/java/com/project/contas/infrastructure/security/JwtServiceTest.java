package com.project.contas.infrastructure.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", "c2VjcmV0LWtleS1mb3ItZGVzYWZpby1iYWNrZW5kLTIwMjY=");
    }

    @Test
    public void gerarToken_retornaTokenNaoNulo() {
        String token = jwtService.gerarToken("desafio");
        assertNotNull(token);
    }

    @Test
    public void validarToken_comTokenValido_retornaTrue() {
        String token = jwtService.gerarToken("desafio");
        assertTrue(jwtService.validarToken(token));
    }

    @Test
    public void validarToken_comTokenInvalido_retornaFalse() {
        assertFalse(jwtService.validarToken("token-invalido"));
    }

    @Test
    public void extrairUsuario_retornaUsernameCorreto() {
        String token = jwtService.gerarToken("desafio");
        assertEquals("desafio", jwtService.extrairUsuario(token));
    }
}
