package com.cabral.usuarioapi.business.converter;

import com.cabral.usuarioapi.business.dto.EnderecoDTO;
import com.cabral.usuarioapi.business.dto.TelefoneDTO;
import com.cabral.usuarioapi.business.dto.UsuarioDTO;
import com.cabral.usuarioapi.insfrastructure.entity.Endereco;
import com.cabral.usuarioapi.insfrastructure.entity.Telefone;
import com.cabral.usuarioapi.insfrastructure.entity.Usuario;

import java.util.List;

public class UsuarioConverter {

    public Usuario paraUsuarioEntity(UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setEnderecos(paraListaEndereco(usuarioDTO.getEnderecos()));
        usuario.setTelefones(paraListaTelefones(usuarioDTO.getTelefones()));
        return usuario;
    }


    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS){
        return enderecoDTOS.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO){
        Endereco endereco = new Endereco();
        endereco.getRua();
        endereco.getNumero();
        endereco.getComplemento();
        endereco.getCidade();
        endereco.getEstado();
        endereco.getCep();
        return endereco;
    }

    public List<Telefone> paraListaTelefones(List<TelefoneDTO> telefoneDTOS){
        return telefoneDTOS.stream().map(this::paraTelefoneEntity).toList();
    }

    public Telefone paraTelefoneEntity(TelefoneDTO telefoneDTO){
        Telefone telefone = new Telefone();
        telefone.getNumero();
        telefone.getDdd();
        return telefone;
    }


    public UsuarioDTO paraUsuarioDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setSenha(usuario.getSenha());
        return dto;
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecos) {
        return enderecos.stream()
                .map(this::paraEnderecoDTO)
                .toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco) {
        EnderecoDTO dto = new EnderecoDTO();
        dto.setRua(endereco.getRua());
        dto.setNumero(String.valueOf(endereco.getNumero()));
        dto.setComplemento(endereco.getComplemento());
        dto.setCidade(endereco.getCidade());
        dto.setEstado(endereco.getEstado());
        dto.setCep(endereco.getCep());
        return dto;
    }

    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefones) {
        return telefones.stream()
                .map(this::paraTelefoneDTO)
                .toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone) {
        TelefoneDTO dto = new TelefoneDTO();
        dto.setNumero(telefone.getNumero());
        dto.setDdd(telefone.getDdd());
        return dto;
    }



}
