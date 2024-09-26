package com.project.contas.presentation.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.contas.application.service.ContaService;
import com.project.contas.dto.AtualizarSituacaoContaDTO;
import com.project.contas.dto.CadastrarContaDTO;
import com.project.contas.dto.ContaDTO;

@RestController
@RequestMapping(value = "/conta")
public class ContaController {

	@Autowired
	private ContaService contaService;

	@PostMapping
	public ResponseEntity<Boolean> cadastrarConta(@RequestBody CadastrarContaDTO cadastrarContaDTO) {
		this.contaService.cadastrarConta(cadastrarContaDTO);
		return ResponseEntity.ok(true);
	}

    @PutMapping
    public ResponseEntity<Boolean> atualizarConta(@RequestBody ContaDTO contaDTO) {
    	this.contaService.atualizarConta(contaDTO);
    	return ResponseEntity.ok(true);
    }

    @PatchMapping
    public ResponseEntity<Boolean> atualizarSituacaoConta(@RequestBody AtualizarSituacaoContaDTO atualizarSituacaoContaDTO) {
    	this.contaService.atualizarSituacaoConta(atualizarSituacaoContaDTO);
    	return ResponseEntity.ok(true);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ContaDTO> buscarContaPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(this.contaService.buscarContaPorId(id));
    }

	@GetMapping(path = "/listar-contas")
	public ResponseEntity<List<ContaDTO>> listarContas(
			@RequestParam(name = "data-vencimento-inicial", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataVencimentoInicial,
			@RequestParam(name = "data-vencimento-final", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataVencimentoFinal,
			@RequestParam(name = "descricao", required = false) String descricao,
			@RequestParam(name = "pagina", required = false, defaultValue = "0") Integer pagina,
			@RequestParam(name = "maximo-por-pagina", required = false, defaultValue = "10") Integer maximoPorPagina
	) {
		List<ContaDTO> listaDeContas = this.contaService.listarContasPorFiltro(dataVencimentoInicial, dataVencimentoFinal, descricao, PageRequest.of(pagina, maximoPorPagina));

		return ResponseEntity.ok(listaDeContas);
	}

    @GetMapping(path = "/carregar-valor-pago")
    public ResponseEntity<Double> carregarValorPagoPorPeriodo(
            @RequestParam(name = "data-inicial") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam(name = "data-final") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal
    ) {
        Double valorTotal = this.contaService.carregarValorPagoPorPeriodo(dataInicial, dataFinal);

        return ResponseEntity.ok(valorTotal);
    }

    @PostMapping(path = "/importar-conta")
    public ResponseEntity<Boolean> importarContasCSV(@RequestBody MultipartFile file) {
        return ResponseEntity.ok(this.contaService.importarContasCSV(file));
    }

}
