package com.cadcliente.clientecrediario.repository;

import com.cadcliente.clientecrediario.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByCpf(String cpf);

    boolean existsByCpf(String cpf);
}
