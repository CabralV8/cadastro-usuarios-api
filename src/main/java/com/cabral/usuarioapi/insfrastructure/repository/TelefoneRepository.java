package com.cabral.usuarioapi.insfrastructure.repository;


import com.cabral.usuarioapi.insfrastructure.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
