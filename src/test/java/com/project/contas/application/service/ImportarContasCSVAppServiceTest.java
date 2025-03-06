//package com.project.contas.application.service;
//
//import com.opencsv.bean.CsvToBean;
//import com.opencsv.bean.CsvToBeanBuilder;
//import com.project.contas.application.usecase.ImportarContasCSVUseCase;
//import com.project.contas.domain.Conta;
//import com.project.contas.domain.repository.ContaRepository;
//import com.project.contas.dto.CadastrarContaCSV;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.util.List;
//
//@Service
//@Transactional
//public class ImportarContasCSVAppServiceTest implements ImportarContasCSVUseCase {
//
//    private ContaRepository contaRepository;
//
//    @Override
//    public Boolean executar(MultipartFile file) {
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
//            CsvToBean<CadastrarContaCSV> csvToBean = new CsvToBeanBuilder<CadastrarContaCSV>(reader)
//                    .withType(CadastrarContaCSV.class)
//                    .withIgnoreLeadingWhiteSpace(true)
//                    .build();
//
//            List<CadastrarContaCSV> cadastrarContaCSVS = csvToBean.parse();
//
//            if (cadastrarContaCSVS == null || cadastrarContaCSVS.isEmpty()) {
//                return Boolean.FALSE;
//            }
//
//            cadastrarContaCSVS.forEach(contaCSV -> this.contaRepository.save(Conta.cadastrarConta(contaCSV)));
//        } catch (Exception e) {
//            return Boolean.FALSE;
//        }
//
//        return Boolean.TRUE;
//    }
//
//    @Autowired
//    public void setContaRepository(ContaRepository contaRepository) {
//        this.contaRepository = contaRepository;
//    }
//}
