package com.cabral.usuarioapi.business.dto;

import java.util.List;

public record UsuarioDTORecord(String nome,
                               String email,
                               String senha,
                               List<EnderecoDTORecord> enderecos,
                               List<TeleFoneDTORecord> telefones) {
}
