package com.project.contas.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "FORNECEDOR")
public class Fornecedor {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String nome;

    protected Fornecedor() {
    }

    public static Fornecedor cadastrarFornecedor(String nome) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.id = UUID.randomUUID();
        fornecedor.nome = nome;
        return fornecedor;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
