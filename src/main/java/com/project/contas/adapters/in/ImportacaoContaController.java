package com.project.contas.adapters.in;

import com.project.contas.application.usecase.BuscarImportacaoContaUseCase;
import com.project.contas.domain.dto.ImportacaoContaDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Importações")
@RestController
@RequestMapping(value = "/importacao")
public class ImportacaoContaController {

    private final BuscarImportacaoContaUseCase buscarImportacaoContaUseCase;

    public ImportacaoContaController(BuscarImportacaoContaUseCase buscarImportacaoContaUseCase) {
        this.buscarImportacaoContaUseCase = buscarImportacaoContaUseCase;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ImportacaoContaDTO> buscarImportacao(@PathVariable UUID id) {
        return ResponseEntity.ok(this.buscarImportacaoContaUseCase.executar(id));
    }
}
