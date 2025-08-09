package com.cabral.usuarioapi.business.dto;

public record EnderecoDTORecord(Long id,
                                String rua,
                                String numero,
                                String complemento,
                                String cidade,
                                String estado,
                                String cep) {
}

