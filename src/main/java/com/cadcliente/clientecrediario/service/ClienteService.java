package com.cadcliente.clientecrediario.service;

import com.cadcliente.clientecrediario.DTOs.ClienteRequestDTO;
import com.cadcliente.clientecrediario.DTOs.ClienteResponseDTO;
import com.cadcliente.clientecrediario.model.Cliente;
import com.cadcliente.clientecrediario.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Converter Model → ResponseDTO
    private ClienteResponseDTO toResponseDTO(Cliente cliente) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setCpf(cliente.getCpf());
        dto.setRg(cliente.getRg());
        dto.setTelefone(cliente.getTelefone());
        dto.setEmail(cliente.getEmail());
        dto.setRendimentoMensal(cliente.getRendimentoMensal());
        return dto;
    }

    // Converter RequestDTO → Model
    private Cliente toModel(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setRg(dto.getRg());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEmail(dto.getEmail());
        cliente.setRendimentoMensal(dto.getRendimentoMensal());
        return cliente;
    }

    public List<ClienteResponseDTO> buscarTodosClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO buscarClientePorId(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente com ID " + id + " não encontrado."));
        return toResponseDTO(cliente);
    }

    public ClienteResponseDTO buscarClientePorCpf(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new NoSuchElementException("Cliente com CPF " + cpf + " não encontrado."));
        return toResponseDTO(cliente);
    }

    public ClienteResponseDTO salvarCliente(ClienteRequestDTO dto) {
        Cliente cliente = toModel(dto);
        return toResponseDTO(clienteRepository.save(cliente));
    }

    public void deletarCliente(Integer id) {
        clienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente com ID " + id + " não encontrado."));
        clienteRepository.deleteById(id);
    }

    public ClienteResponseDTO atualizarClientePorId(Integer id, ClienteRequestDTO dto) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente com ID " + id + " não encontrado."));
        clienteExistente.setNome(dto.getNome());
        clienteExistente.setCpf(dto.getCpf());
        clienteExistente.setRg(dto.getRg());
        clienteExistente.setTelefone(dto.getTelefone());
        clienteExistente.setEmail(dto.getEmail());
        clienteExistente.setRendimentoMensal(dto.getRendimentoMensal());
        return toResponseDTO(clienteRepository.save(clienteExistente));
    }
}