package com.project.contas.adapters.in;

import com.project.contas.application.usecase.*;
import com.project.contas.domain.dto.AtualizarSituacaoContaDTO;
import com.project.contas.domain.dto.CadastrarContaDTO;
import com.project.contas.domain.dto.ContaDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Tag(name = "Contas")
@RestController
@RequestMapping(value = "/conta")
public class ContaController {

    private final CadastrarContaUseCase cadastrarContaUseCase;
    private final AtualizarContaUseCase atualizarContaUseCase;
    private final AtualizarSituacaoContaUseCase atualizarSituacaoContaUseCase;
    private final BuscarContaPorIdUseCase buscarContaPorIdUseCase;
    private final ListarContasUseCase listarContasUseCase;
    private final CarregarValorPagoUseCase carregarValorPagoUseCase;
    private final ImportarContasCSVUseCase importarContasCSVUseCase;

    public ContaController(CadastrarContaUseCase cadastrarContaUseCase, AtualizarContaUseCase atualizarContaUseCase,
                           AtualizarSituacaoContaUseCase atualizarSituacaoContaUseCase,
                           BuscarContaPorIdUseCase buscarContaPorIdUseCase, ListarContasUseCase listarContasUseCase,
                           CarregarValorPagoUseCase carregarValorPagoUseCase, ImportarContasCSVUseCase importarContasCSVUseCase) {
        this.cadastrarContaUseCase = cadastrarContaUseCase;
        this.atualizarContaUseCase = atualizarContaUseCase;
        this.atualizarSituacaoContaUseCase = atualizarSituacaoContaUseCase;
        this.buscarContaPorIdUseCase = buscarContaPorIdUseCase;
        this.listarContasUseCase = listarContasUseCase;
        this.carregarValorPagoUseCase = carregarValorPagoUseCase;
        this.importarContasCSVUseCase = importarContasCSVUseCase;
    }

    @PostMapping
    public ResponseEntity<Boolean> cadastrarConta(@RequestBody @Valid CadastrarContaDTO cadastrarContaDTO) {
        this.cadastrarContaUseCase.executar(cadastrarContaDTO);
        return ResponseEntity.ok(true);
    }

    @PutMapping
    public ResponseEntity<Boolean> atualizarConta(@RequestBody @Valid ContaDTO contaDTO) {
        this.atualizarContaUseCase.executar(contaDTO);
        return ResponseEntity.ok(true);
    }

    @PatchMapping
    public ResponseEntity<Boolean> atualizarSituacaoConta(@RequestBody @Valid AtualizarSituacaoContaDTO atualizarSituacaoContaDTO) {
        this.atualizarSituacaoContaUseCase.executar(atualizarSituacaoContaDTO);
        return ResponseEntity.ok(true);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ContaDTO> buscarContaPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(this.buscarContaPorIdUseCase.executar(id));
    }

    @GetMapping(path = "/listar-contas")
    public ResponseEntity<List<ContaDTO>> listarContas(
            @RequestParam(name = "data-vencimento-inicial", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataVencimentoInicial,
            @RequestParam(name = "data-vencimento-final", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataVencimentoFinal,
            @RequestParam(name = "descricao", required = false) String descricao,
            @RequestParam(name = "pagina", required = false, defaultValue = "0") Integer pagina,
            @RequestParam(name = "maximo-por-pagina", required = false, defaultValue = "10") Integer maximoPorPagina
    ) {
        return ResponseEntity.ok(this.listarContasUseCase.executar(dataVencimentoInicial, dataVencimentoFinal, descricao,
                PageRequest.of(pagina, maximoPorPagina)));
    }

    @GetMapping(path = "/carregar-valor-pago")
    public ResponseEntity<BigDecimal> carregarValorPagoPorPeriodo(
            @RequestParam(name = "data-inicial") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam(name = "data-final") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal
    ) {
        return ResponseEntity.ok(this.carregarValorPagoUseCase.executar(dataInicial, dataFinal));
    }

    @PostMapping(path = "/importar-conta")
    public ResponseEntity<UUID> importarContasCSV(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(this.importarContasCSVUseCase.executar(file));
    }
}
