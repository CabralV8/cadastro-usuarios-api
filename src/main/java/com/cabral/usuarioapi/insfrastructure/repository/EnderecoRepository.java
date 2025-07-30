package com.cabral.usuarioapi.insfrastructure.repository;


import com.cabral.usuarioapi.insfrastructure.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
