package com.project.contas.application.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.project.contas.domain.Conta;
import com.project.contas.domain.repository.ContaRepository;
import com.project.contas.dto.AtualizarSituacaoContaDTO;
import com.project.contas.dto.CadastrarContaCSV;
import com.project.contas.dto.CadastrarContaDTO;
import com.project.contas.dto.ContaDTO;

@Service
@Transactional
public class ContaServiceImpl implements ContaService {
	
	@Autowired
	private ContaRepository contaRepository;

	@Override
	public void cadastrarConta(CadastrarContaDTO cadastrarContaDTO) {
		Conta conta = new Conta();
        conta.setId(UUID.randomUUID());
        conta.setDataVencimento(cadastrarContaDTO.dataVencimento());
        conta.setDataPagamento(cadastrarContaDTO.dataPagamento());
        conta.setDescricao(cadastrarContaDTO.descricao());
        conta.setSituacao(cadastrarContaDTO.situacaoContaEnum());
        conta.setValor(cadastrarContaDTO.valor());

		this.contaRepository.save(conta);
	}

	@Override
	public void atualizarConta(ContaDTO contaDTO) {
		this.contaRepository.findById(contaDTO.id()).ifPresent(conta -> this.contaRepository.save(conta.atualizarConta(contaDTO)));
	}

	@Override
	public void atualizarSituacaoConta(AtualizarSituacaoContaDTO atualizarSituacaoContaDTO) {
        this.contaRepository.findById(atualizarSituacaoContaDTO.id()).ifPresent(conta -> {
            if (conta.getSituacao() != null && conta.getSituacao().equals(atualizarSituacaoContaDTO.situacaoContaEnum())) {
                return;
            }

            this.contaRepository.save(conta.atualizarSituacaoConta(atualizarSituacaoContaDTO));
        });
	}

	@Override
	public ContaDTO buscarContaPorId(UUID id) {
		return this.contaRepository.buscarContaDTOPorId(id);
	}

	@Override
	public List<ContaDTO> listarContasPorFiltro(LocalDateTime dataVencimentoInicial, LocalDateTime dataVencimentoFinal, String descricao, Pageable pageable) {
        return this.contaRepository.listarContasPorFiltro(
        		dataVencimentoInicial,
        		dataVencimentoFinal,
                descricao,
                pageable
        );
	}
	
	@Override
	public Double carregarValorPagoPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
		return this.contaRepository.carregarValorPagoPorPeriodo(dataInicial, dataFinal);
	}

	@Override
	public Boolean importarContasCSV(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<CadastrarContaCSV> csvToBean = new CsvToBeanBuilder<CadastrarContaCSV>(reader)
                    .withType(CadastrarContaCSV.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<CadastrarContaCSV> cadastrarContaCSVS = csvToBean.parse();

            if (cadastrarContaCSVS == null || cadastrarContaCSVS.isEmpty()) {
                return false;
            }

            cadastrarContaCSVS.forEach(contaCSV -> this.contaRepository.save(Conta.cadastrarConta(contaCSV)));
        } catch (Exception e) {
            return false;
        }

        return true;
	}

}
