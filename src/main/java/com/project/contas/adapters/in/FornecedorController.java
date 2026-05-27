package com.project.contas.adapters.in;

import com.project.contas.application.usecase.AtualizarFornecedorUseCase;
import com.project.contas.application.usecase.BuscarFornecedorPorIdUseCase;
import com.project.contas.application.usecase.CadastrarFornecedorUseCase;
import com.project.contas.application.usecase.DeletarFornecedorUseCase;
import com.project.contas.application.usecase.ListarFornecedoresUseCase;
import com.project.contas.domain.dto.CadastrarFornecedorDTO;
import com.project.contas.domain.dto.FornecedorDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "Fornecedores")
@RestController
@RequestMapping(value = "/fornecedor")
public class FornecedorController {

    private final CadastrarFornecedorUseCase cadastrarFornecedorUseCase;
    private final BuscarFornecedorPorIdUseCase buscarFornecedorPorIdUseCase;
    private final AtualizarFornecedorUseCase atualizarFornecedorUseCase;
    private final DeletarFornecedorUseCase deletarFornecedorUseCase;
    private final ListarFornecedoresUseCase listarFornecedoresUseCase;

    public FornecedorController(CadastrarFornecedorUseCase cadastrarFornecedorUseCase,
                                BuscarFornecedorPorIdUseCase buscarFornecedorPorIdUseCase,
                                AtualizarFornecedorUseCase atualizarFornecedorUseCase,
                                DeletarFornecedorUseCase deletarFornecedorUseCase,
                                ListarFornecedoresUseCase listarFornecedoresUseCase) {
        this.cadastrarFornecedorUseCase = cadastrarFornecedorUseCase;
        this.buscarFornecedorPorIdUseCase = buscarFornecedorPorIdUseCase;
        this.atualizarFornecedorUseCase = atualizarFornecedorUseCase;
        this.deletarFornecedorUseCase = deletarFornecedorUseCase;
        this.listarFornecedoresUseCase = listarFornecedoresUseCase;
    }

    @PostMapping
    public ResponseEntity<Boolean> cadastrarFornecedor(@RequestBody @Valid CadastrarFornecedorDTO cadastrarFornecedorDTO) {
        this.cadastrarFornecedorUseCase.executar(cadastrarFornecedorDTO);
        return ResponseEntity.status(201).body(true);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<FornecedorDTO> buscarFornecedorPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(this.buscarFornecedorPorIdUseCase.executar(id));
    }

    @GetMapping
    public ResponseEntity<List<FornecedorDTO>> listarFornecedores(
            @RequestParam(name = "pagina", required = false, defaultValue = "0") Integer pagina,
            @RequestParam(name = "maximo-por-pagina", required = false, defaultValue = "10") Integer maximoPorPagina
    ) {
        return ResponseEntity.ok(this.listarFornecedoresUseCase.executar(PageRequest.of(pagina, maximoPorPagina)));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Boolean> atualizarFornecedor(@PathVariable UUID id,
                                                       @RequestBody @Valid CadastrarFornecedorDTO dto) {
        this.atualizarFornecedorUseCase.executar(id, dto);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletarFornecedor(@PathVariable UUID id) {
        this.deletarFornecedorUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }
}
