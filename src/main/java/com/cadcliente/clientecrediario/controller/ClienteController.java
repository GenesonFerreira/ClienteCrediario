package com.cadcliente.clientecrediario.controller;

import com.cadcliente.clientecrediario.DTOs.ClienteRequestDTO;
import com.cadcliente.clientecrediario.DTOs.ClienteResponseDTO;
import com.cadcliente.clientecrediario.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponseDTO salvarCliente(@Valid @RequestBody ClienteRequestDTO dto) {
        return clienteService.salvarCliente(dto);
    }

    @GetMapping
    public List<ClienteResponseDTO> buscarTodosClientes() {
        return clienteService.buscarTodosClientes();
    }

    @GetMapping("/buscar/id/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarClientePorId(@PathVariable Integer id) {
        return ResponseEntity.ok(clienteService.buscarClientePorId(id));
    }

    @GetMapping("/buscar/cpf/{cpf}")
    public ResponseEntity<ClienteResponseDTO> buscarClientePorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(clienteService.buscarClientePorCpf(cpf));
    }

    @DeleteMapping("/deletar/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarClienteId(@PathVariable Integer id) {
        clienteService.deletarCliente(id);
    }

    @DeleteMapping("/deletar/cpf/{cpf}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarClienteCpf(@PathVariable String cpf) {
        ClienteResponseDTO cliente = clienteService.buscarClientePorCpf(cpf);
        clienteService.deletarCliente(cliente.getId());
    }

    @PutMapping("/atualizar/id/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizarClientePorId(
            @PathVariable Integer id,
            @Valid @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.ok(clienteService.atualizarClientePorId(id, dto));
    }

    @PutMapping("/atualizar/cpf/{cpf}")
    public ResponseEntity<ClienteResponseDTO> atualizarClientePorCpf(
            @PathVariable String cpf,
            @Valid @RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO clienteExistente = clienteService.buscarClientePorCpf(cpf);
        return ResponseEntity.ok(clienteService.atualizarClientePorId(clienteExistente.getId(), dto));
    }
}