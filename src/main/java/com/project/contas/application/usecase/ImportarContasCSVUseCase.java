package com.project.contas.application.usecase;

import org.springframework.web.multipart.MultipartFile;

public interface ImportarContasCSVUseCase {

    Boolean executar(MultipartFile file);
}
