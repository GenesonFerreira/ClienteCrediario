package com.cadcliente.clientecrediario.DTOs;

import com.cadcliente.clientecrediario.model.TipoRelacionamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmprestimoResponseDTO {

    private Integer id;
    private Double valorInicial;
    private Double valorFinal;
    private TipoRelacionamento relacionamento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicial;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFinal;

    // Dados resumidos do cliente dono do empréstimo
    private Integer clienteId;
    private String clienteNome;
    private String clienteCpf;
}