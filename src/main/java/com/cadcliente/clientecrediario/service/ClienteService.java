package com.cadcliente.clientecrediario.service;

import com.cadcliente.clientecrediario.model.Cliente;
import com.cadcliente.clientecrediario.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    //Listar todos os clientes
    public List<Cliente> buscarTodosClientes(){
        return clienteRepository.findAll();
    }
    // Buscar Cliente por ID
    public Cliente buscarClientePorId(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("Cliente com ID " + id + " não encontrado na base de dados."));
    }
    // Buscar Cliente por CPF
    public Cliente buscarClientePorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf)
                .orElseThrow(() ->
                        new NoSuchElementException("Cliente com CPF " + cpf + " não encontrado na base de dados."));
    }
    //Salvar Cliente
    public Cliente salvarCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }
    //Deletar Cliente
    public void deletarCliente(Integer id){
        clienteRepository.deleteById(id);
    }
    //Atualizar o Cliente pelo ID
    public Cliente atualizarClientePorId(Integer id, Cliente clienteAtualizado){
        Cliente clienteExistente = buscarClientePorId(id);
        if (clienteExistente != null){
            clienteExistente.setNome(clienteAtualizado.getNome());
            clienteExistente.setCpf(clienteAtualizado.getCpf());
            clienteExistente.setRg(clienteAtualizado.getRg());
            clienteExistente.setTelefone(clienteAtualizado.getTelefone());
            clienteExistente.setEmail(clienteAtualizado.getEmail());
            clienteExistente.setRendimentoMensal(clienteAtualizado.getRendimentoMensal());
            return clienteRepository.save(clienteExistente);
        }
        return null;
    }

}
