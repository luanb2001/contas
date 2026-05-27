package com.project.contas.adapters.in;

import com.project.contas.application.usecase.BuscarFornecedorPorIdUseCase;
import com.project.contas.application.usecase.CadastrarFornecedorUseCase;
import com.project.contas.domain.dto.CadastrarFornecedorDTO;
import com.project.contas.domain.dto.FornecedorDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Fornecedores")
@RestController
@RequestMapping(value = "/fornecedor")
public class FornecedorController {

    private final CadastrarFornecedorUseCase cadastrarFornecedorUseCase;
    private final BuscarFornecedorPorIdUseCase buscarFornecedorPorIdUseCase;

    public FornecedorController(CadastrarFornecedorUseCase cadastrarFornecedorUseCase,
                                BuscarFornecedorPorIdUseCase buscarFornecedorPorIdUseCase) {
        this.cadastrarFornecedorUseCase = cadastrarFornecedorUseCase;
        this.buscarFornecedorPorIdUseCase = buscarFornecedorPorIdUseCase;
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
}
