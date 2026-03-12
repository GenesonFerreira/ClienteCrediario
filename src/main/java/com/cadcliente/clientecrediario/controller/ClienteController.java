package com.cadcliente.clientecrediario.controller;

import com.cadcliente.clientecrediario.model.Cliente;
import com.cadcliente.clientecrediario.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    //Salvar Cliente ( OK )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvarCliente(@Valid @RequestBody Cliente cliente) {
        clienteService.salvarCliente(cliente);
    }

    //Listar Clientes ( OK )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> buscarTodosClientes() {
        return clienteService.buscarTodosClientes();
    }

    //Buscar Clientes por ID ( OK )
    @GetMapping("/buscar/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Integer id) {
        Cliente cliente = clienteService.buscarClientePorId(id);
        return ResponseEntity.ok(cliente);
    }

    //Buscar Clientes por CPF ( OK )
    @GetMapping("/buscar/cpf/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Cliente> buscarClientePorCpf(@PathVariable String cpf) {
        Cliente cliente = clienteService.buscarClientePorCpf(cpf);
        return ResponseEntity.ok(cliente);
    }

    //Deletar Cliente por ID ( OK )
    @DeleteMapping("/deletar/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarClienteId(@PathVariable Integer id) {
        clienteService.deletarCliente(id);
    }

    //Deletar Cliente por CPF ( OK )
    @DeleteMapping("/deletar/cpf/{cpf}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarClienteCpf(@PathVariable String cpf) {
        clienteService.deletarCliente(clienteService.buscarClientePorCpf(cpf).getId());
    }

    //Atualizar Cliente por ID
    @PutMapping("/atualizar/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Cliente> atualizarClientePorId(@PathVariable Integer id, @Valid @RequestBody Cliente clienteAtualizado) {
        Cliente cliente = clienteService.atualizarClientePorId(id, clienteAtualizado);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Atualizar Cliente por CPF
    @PutMapping("/atualizar/cpf/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Cliente> atualizarClientePorCpf(@PathVariable String cpf, @Valid @RequestBody Cliente clienteAtualizado) {
        Cliente clienteExistente = clienteService.buscarClientePorCpf(cpf);
        if (clienteExistente != null) {
            Cliente clienteAtualizadoResult = clienteService.atualizarClientePorId(clienteExistente.getId(), clienteAtualizado);
            return ResponseEntity.ok(clienteAtualizadoResult);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
