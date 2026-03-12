package com.cadcliente.clientecrediario.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    @Column(unique = true, nullable = false)
    private String cpf;
    private String rg;
    private String telefone;
    private String email;
    private double rendimentoMensal;
}
