package com.cadcliente.clientecrediario.service;

import com.cadcliente.clientecrediario.DTOs.EmprestimoRequestDTO;
import com.cadcliente.clientecrediario.DTOs.EmprestimoResponseDTO;
import com.cadcliente.clientecrediario.model.Cliente;
import com.cadcliente.clientecrediario.model.Emprestimo;
import com.cadcliente.clientecrediario.model.TipoRelacionamento;
import com.cadcliente.clientecrediario.repository.ClienteRepository;
import com.cadcliente.clientecrediario.repository.EmprestimoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class EmprestimoService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    private EmprestimoResponseDTO toResponseDTO(Emprestimo emprestimo) {
        EmprestimoResponseDTO dto = new EmprestimoResponseDTO();
        dto.setId(emprestimo.getId());
        dto.setValorInicial(emprestimo.getValorInicial());
        dto.setValorFinal(emprestimo.getValorFinal());
        dto.setRelacionamento(emprestimo.getRelacionamento());
        dto.setDataInicial(emprestimo.getDataInicial());
        dto.setDataFinal(emprestimo.getDataFinal());
        // Dados resumidos do cliente
        dto.setClienteId(emprestimo.getCliente().getId());
        dto.setClienteNome(emprestimo.getCliente().getNome());
        dto.setClienteCpf(emprestimo.getCliente().getCpf());
        return dto;
    }

    private Double calcularValorFinal(Double valorInicial, TipoRelacionamento relacionamento, int quantidadeEmprestimosAtuais) {
        switch (relacionamento) {
            case BRONZE:
                return valorInicial * 1.80;
            case PRATA:
                if (valorInicial > 5000) {
                    return valorInicial * 1.40;
                } else {
                    return valorInicial * 1.60;
                }
            case OURO:
                if (quantidadeEmprestimosAtuais == 0) {
                    return valorInicial * 1.20;
                } else {
                    return valorInicial * 1.30;
                }
            default:
                return valorInicial;
        }
    }

    private void validarLimiteCredito(Cliente cliente, Double valorNovoEmprestimo, Integer ignorarEmprestimoId) {
        double limiteTotal = cliente.getRendimentoMensal() * 10;


        double totalEmprestimosAtuais = cliente.getEmprestimos().stream()
                .filter(emp -> !emp.getId().equals(ignorarEmprestimoId))
                .mapToDouble(Emprestimo::getValorInicial)
                .sum();

        if (valorNovoEmprestimo > limiteTotal) {
            throw new IllegalArgumentException(
                    "Valor do empréstimo excede o limite de crédito do cliente. " +
                            "Limite: R$" + limiteTotal
            );
        }

        if (totalEmprestimosAtuais + valorNovoEmprestimo > limiteTotal) {
            throw new IllegalArgumentException(
                    "Soma dos empréstimos excede o limite de crédito do cliente. " +
                            "Limite: R$" + limiteTotal + " | " +
                            "Já utilizado: R$" + totalEmprestimosAtuais
            );
        }
    }

    public EmprestimoResponseDTO adicionarEmprestimo(Integer clienteId, EmprestimoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NoSuchElementException("Cliente com ID " + clienteId + " não encontrado."));

        validarLimiteCredito(cliente, dto.getValorInicial(), -1);

        int quantidadeEmprestimos = cliente.getEmprestimos().size();
        Double valorFinal = calcularValorFinal(dto.getValorInicial(), dto.getRelacionamento(), quantidadeEmprestimos);

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setCliente(cliente);
        emprestimo.setValorInicial(dto.getValorInicial());
        emprestimo.setValorFinal(valorFinal);
        emprestimo.setRelacionamento(dto.getRelacionamento());
        emprestimo.setDataInicial(dto.getDataInicial());
        emprestimo.setDataFinal(dto.getDataFinal());

        return toResponseDTO(emprestimoRepository.save(emprestimo));
    }

    public List<EmprestimoResponseDTO> listarEmprestimosPorClienteId(Integer clienteId) {
        clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NoSuchElementException("Cliente com ID " + clienteId + " não encontrado."));

        return emprestimoRepository.findByClienteId(clienteId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EmprestimoResponseDTO> listarEmprestimosPorCpf(String cpf) {
        clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new NoSuchElementException("Cliente com CPF " + cpf + " não encontrado."));

        return emprestimoRepository.findByClienteCpf(cpf)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public EmprestimoResponseDTO atualizarEmprestimo(Integer emprestimoId, EmprestimoRequestDTO dto) {
        Emprestimo emprestimoExistente = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new NoSuchElementException("Empréstimo com ID " + emprestimoId + " não encontrado."));

        Cliente cliente = emprestimoExistente.getCliente();

        validarLimiteCredito(cliente, dto.getValorInicial(), emprestimoId);

        int quantidadeEmprestimos = cliente.getEmprestimos().size();
        Double valorFinal = calcularValorFinal(dto.getValorInicial(), dto.getRelacionamento(), quantidadeEmprestimos);

        emprestimoExistente.setValorInicial(dto.getValorInicial());
        emprestimoExistente.setValorFinal(valorFinal);
        emprestimoExistente.setRelacionamento(dto.getRelacionamento());
        emprestimoExistente.setDataInicial(dto.getDataInicial());
        emprestimoExistente.setDataFinal(dto.getDataFinal());

        return toResponseDTO(emprestimoRepository.save(emprestimoExistente));
    }

    public void deletarEmprestimo(Integer emprestimoId) {
        emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new NoSuchElementException("Empréstimo com ID " + emprestimoId + " não encontrado."));

        emprestimoRepository.deleteById(emprestimoId);
    }
}