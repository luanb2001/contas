package com.project.contas.application.exception;

public class PagamentoInvalidoException extends RuntimeException {
    public PagamentoInvalidoException(String message) {
        super(message);
    }
}
