package com.cadcliente.clientecrediario.repository;

import com.cadcliente.clientecrediario.model.Cliente;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    //Page<Cliente> findAll(Pageable pageable);
}
