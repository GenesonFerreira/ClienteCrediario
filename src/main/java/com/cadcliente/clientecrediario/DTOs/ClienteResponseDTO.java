package com.cadcliente.clientecrediario.DTOs;

import lombok.Data;

@Data
public class ClienteResponseDTO {
    private Integer id;
    private String nome;
    private String cpf;
    private String rg;
    private String telefone;
    private String email;
    private double rendimentoMensal;
}