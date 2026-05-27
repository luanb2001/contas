package com.project.contas.adapters.in;

import com.project.contas.application.exception.RegraNegocioException;
import com.project.contas.domain.dto.LoginDTO;
import com.project.contas.domain.dto.TokenDTO;
import com.project.contas.infrastructure.security.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Autenticação")
@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private static final String USERNAME = "desafio";
    private static final String PASSWORD = "contas";

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        if (!USERNAME.equals(loginDTO.username()) || !PASSWORD.equals(loginDTO.password())) {
            throw new RegraNegocioException("Credenciais inválidas");
        }
        String token = jwtService.gerarToken(loginDTO.username());
        return ResponseEntity.ok(new TokenDTO(token));
    }
}
