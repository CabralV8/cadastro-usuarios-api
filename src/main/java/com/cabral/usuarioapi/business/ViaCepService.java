package com.cabral.usuarioapi.business;

import com.cabral.usuarioapi.insfrastructure.clients.ViaCepClient;
import com.cabral.usuarioapi.insfrastructure.clients.ViaCepRecord;
import com.cabral.usuarioapi.insfrastructure.exceptions.IllegalArgumentException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ViaCepService {

    private final ViaCepClient client;


    public ViaCepService(ViaCepClient client) {
        this.client = client;
    }

    public ViaCepRecord buscarDadosEndereco(String cep){
        return client.buscarDadosEndereco(processarCep(cep));
    }

    private String processarCep(String cep){
        String cepFormatado = cep.replace(" ", "")
                .replace("-", "");

        if (!cepFormatado.matches("\\d+")
                || !Objects.equals(cepFormatado.length(), 8)){
            throw new IllegalArgumentException("Cep contém caracteres inválidos, favor verificar.");
        }
        return cepFormatado;
    }
}
