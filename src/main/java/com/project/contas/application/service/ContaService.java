package com.project.contas.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.project.contas.dto.AtualizarSituacaoContaDTO;
import com.project.contas.dto.CadastrarContaDTO;
import com.project.contas.dto.ContaDTO;

public interface ContaService {
	
	public void cadastrarConta(CadastrarContaDTO cadastrarContaDTO);

	public void atualizarConta(ContaDTO contaDTO);
	
	public void atualizarSituacaoConta(AtualizarSituacaoContaDTO atualizarSituacaoContaDTO);
	
	public ContaDTO buscarContaPorId(UUID id);
	
	public List<ContaDTO> listarContasPorFiltro(LocalDateTime dataVencimentoInicial, LocalDateTime dataVencimentoFinal, String descricao, Pageable pageable);
	
	public Double carregarValorPagoPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal);
	
	public Boolean importarContasCSV(MultipartFile file);

}