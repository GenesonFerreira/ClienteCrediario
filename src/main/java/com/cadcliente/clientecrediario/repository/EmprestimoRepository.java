package com.cadcliente.clientecrediario.repository;

import com.cadcliente.clientecrediario.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer> {


    List<Emprestimo> findByClienteId(Integer clienteId);

    List<Emprestimo> findByClienteCpf(String cpf);

    boolean existsByClienteId(Integer clienteId);
}