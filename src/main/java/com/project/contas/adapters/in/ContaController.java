package com.project.contas.adapters.in;

import com.project.contas.application.usecase.*;
import com.project.contas.dto.AtualizarSituacaoContaDTO;
import com.project.contas.dto.CadastrarContaDTO;
import com.project.contas.dto.ContaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/conta")
public class ContaController {

	private CadastrarContaUseCase cadastrarContaUseCase;
	private AtualizarContaUseCase atualizarContaUseCase;
	private AtualizarSituacaoContaUseCase atualizarSituacaoContaUseCase;
	private BuscarContaPorIdUseCase buscarContaPorIdUseCase;
	private ListarContasUseCase listarContasUseCase;
	private CarregarValorPagoUseCase carregarValorPagoUseCase;
	private ImportarContasCSVUseCase importarContasCSVUseCase;

	@PostMapping
	public ResponseEntity<Boolean> cadastrarConta(@RequestBody CadastrarContaDTO cadastrarContaDTO) {
		this.cadastrarContaUseCase.executar(cadastrarContaDTO);
		return ResponseEntity.ok(true);
	}

    @PutMapping
    public ResponseEntity<Boolean> atualizarConta(@RequestBody ContaDTO contaDTO) {
    	this.atualizarContaUseCase.executar(contaDTO);
    	return ResponseEntity.ok(true);
    }

    @PatchMapping
    public ResponseEntity<Boolean> atualizarSituacaoConta(@RequestBody AtualizarSituacaoContaDTO atualizarSituacaoContaDTO) {
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
		List<ContaDTO> listaDeContas = this.listarContasUseCase.executar(dataVencimentoInicial, dataVencimentoFinal, descricao, PageRequest.of(pagina, maximoPorPagina));

		return ResponseEntity.ok(listaDeContas);
	}

    @GetMapping(path = "/carregar-valor-pago")
    public ResponseEntity<Double> carregarValorPagoPorPeriodo(
            @RequestParam(name = "data-inicial") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam(name = "data-final") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal
    ) {
        Double valorTotal = this.carregarValorPagoUseCase.executar(dataInicial, dataFinal);

        return ResponseEntity.ok(valorTotal);
    }

    @PostMapping(path = "/importar-conta")
    public ResponseEntity<Boolean> importarContasCSV(@RequestBody MultipartFile file) {
        return ResponseEntity.ok(this.importarContasCSVUseCase.executar(file));
    }

	@Autowired
	public void setCadastrarContaUseCase(CadastrarContaUseCase cadastrarContaUseCase) {
		this.cadastrarContaUseCase = cadastrarContaUseCase;
	}

	@Autowired
	public void setAtualizarContaUseCase(AtualizarContaUseCase atualizarContaUseCase) {
		this.atualizarContaUseCase = atualizarContaUseCase;
	}

	@Autowired
	public void setAtualizarSituacaoContaUseCase(AtualizarSituacaoContaUseCase atualizarSituacaoContaUseCase) {
		this.atualizarSituacaoContaUseCase = atualizarSituacaoContaUseCase;
	}

	@Autowired
	public void setBuscarContaPorIdUseCase(BuscarContaPorIdUseCase buscarContaPorIdUseCase) {
		this.buscarContaPorIdUseCase = buscarContaPorIdUseCase;
	}

	@Autowired
	public void setListarContasUseCase(ListarContasUseCase listarContasUseCase) {
		this.listarContasUseCase = listarContasUseCase;
	}

	@Autowired
	public void setCarregarValorPagoUseCase(CarregarValorPagoUseCase carregarValorPagoUseCase) {
		this.carregarValorPagoUseCase = carregarValorPagoUseCase;
	}

	@Autowired
	public void setImportarContasCSVUseCase(ImportarContasCSVUseCase importarContasCSVUseCase) {
		this.importarContasCSVUseCase = importarContasCSVUseCase;
	}
}
