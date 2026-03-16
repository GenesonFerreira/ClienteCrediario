package com.cadcliente.clientecrediario.DTOs;

import com.cadcliente.clientecrediario.model.TipoRelacionamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmprestimoRequestDTO {

    @NotNull(message = "Valor inicial é obrigatório")
    @Positive(message = "Valor inicial deve ser maior que zero")
    private Double valorInicial;

    @NotNull(message = "Tipo de relacionamento é obrigatório")
    private TipoRelacionamento relacionamento;

    @NotNull(message = "Data inicial é obrigatória")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicial;

    @NotNull(message = "Data final é obrigatória")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFinal;
}