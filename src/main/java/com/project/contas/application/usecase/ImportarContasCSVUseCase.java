package com.project.contas.application.usecase;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ImportarContasCSVUseCase {

    UUID executar(MultipartFile file);
}
