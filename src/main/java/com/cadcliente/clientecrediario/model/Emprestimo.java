package com.cadcliente.clientecrediario.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "cadastro_emprestimo")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotNull(message = "Valor inicial é obrigatório")
    @Positive(message = "Valor inicial deve ser maior que zero")
    private Double valorInicial;

    private Double valorFinal;

    @NotNull(message = "Tipo de relacionamento é obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoRelacionamento relacionamento;

    @NotNull(message = "Data inicial é obrigatória")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicial;

    @NotNull(message = "Data final é obrigatória")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFinal;
}
