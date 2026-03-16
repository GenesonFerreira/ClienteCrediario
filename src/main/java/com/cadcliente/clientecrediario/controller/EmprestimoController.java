package com.cadcliente.clientecrediario.controller;

import com.cadcliente.clientecrediario.DTOs.EmprestimoRequestDTO;
import com.cadcliente.clientecrediario.DTOs.EmprestimoResponseDTO;
import com.cadcliente.clientecrediario.service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    // Adicionar empréstimo a um cliente pelo ID
    @PostMapping("/clientes/{id}/emprestimos")
    public ResponseEntity<EmprestimoResponseDTO> adicionarEmprestimo(
            @PathVariable Integer id,
            @Valid @RequestBody EmprestimoRequestDTO dto) {
        EmprestimoResponseDTO emprestimo = emprestimoService.adicionarEmprestimo(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(emprestimo);
    }

    // Listar empréstimos de um cliente pelo ID
    @GetMapping("/clientes/{id}/emprestimos")
    public ResponseEntity<List<EmprestimoResponseDTO>> listarEmprestimosPorId(
            @PathVariable Integer id) {
        List<EmprestimoResponseDTO> emprestimos = emprestimoService.listarEmprestimosPorClienteId(id);
        return ResponseEntity.ok(emprestimos);
    }

    // Listar empréstimos de um cliente pelo CPF
    @GetMapping("/clientes/cpf/{cpf}/emprestimos")
    public ResponseEntity<List<EmprestimoResponseDTO>> listarEmprestimosPorCpf(
            @PathVariable String cpf) {
        List<EmprestimoResponseDTO> emprestimos = emprestimoService.listarEmprestimosPorCpf(cpf);
        return ResponseEntity.ok(emprestimos);
    }

    // Atualizar empréstimo pelo ID do empréstimo
    @PutMapping("/emprestimos/{id}")
    public ResponseEntity<EmprestimoResponseDTO> atualizarEmprestimo(
            @PathVariable Integer id,
            @Valid @RequestBody EmprestimoRequestDTO dto) {
        EmprestimoResponseDTO emprestimo = emprestimoService.atualizarEmprestimo(id, dto);
        return ResponseEntity.ok(emprestimo);
    }

    // Deletar empréstimo pelo ID do empréstimo
    @DeleteMapping("/emprestimos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarEmprestimo(@PathVariable Integer id) {
        emprestimoService.deletarEmprestimo(id);
    }
}