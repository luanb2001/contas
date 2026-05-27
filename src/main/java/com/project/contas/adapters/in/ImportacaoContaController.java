package com.project.contas.adapters.in;

import com.project.contas.application.usecase.BuscarImportacaoContaUseCase;
import com.project.contas.application.usecase.ListarImportacoesUseCase;
import com.project.contas.domain.dto.ImportacaoContaDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "Importações")
@RestController
@RequestMapping(value = "/importacao")
public class ImportacaoContaController {

    private final BuscarImportacaoContaUseCase buscarImportacaoContaUseCase;
    private final ListarImportacoesUseCase listarImportacoesUseCase;

    public ImportacaoContaController(BuscarImportacaoContaUseCase buscarImportacaoContaUseCase,
                                     ListarImportacoesUseCase listarImportacoesUseCase) {
        this.buscarImportacaoContaUseCase = buscarImportacaoContaUseCase;
        this.listarImportacoesUseCase = listarImportacoesUseCase;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ImportacaoContaDTO> buscarImportacao(@PathVariable UUID id) {
        return ResponseEntity.ok(this.buscarImportacaoContaUseCase.executar(id));
    }

    @GetMapping
    public ResponseEntity<List<ImportacaoContaDTO>> listarImportacoes(
            @RequestParam(name = "pagina", required = false, defaultValue = "0") Integer pagina,
            @RequestParam(name = "maximo-por-pagina", required = false, defaultValue = "10") Integer maximoPorPagina
    ) {
        return ResponseEntity.ok(this.listarImportacoesUseCase.executar(PageRequest.of(pagina, maximoPorPagina)));
    }
}
