package com.cabral.usuarioapi.business.converter;

import com.cabral.usuarioapi.business.dto.EnderecoDTO;
import com.cabral.usuarioapi.business.dto.TelefoneDTO;
import com.cabral.usuarioapi.business.dto.UsuarioDTO;
import com.cabral.usuarioapi.insfrastructure.entity.Endereco;
import com.cabral.usuarioapi.insfrastructure.entity.Telefone;
import com.cabral.usuarioapi.insfrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuarioEntity(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setEnderecos(
                usuario.getEnderecos() != null
                        ? paraListaEnderecoEntity(usuarioDTO.getEnderecos())
                        : null
        );

        usuario.setTelefones(
                usuario.getTelefones() != null
                        ? paraListaTelefones(usuarioDTO.getTelefones())
                        : null
        );
        return usuario;
    }


    public List<Endereco> paraListaEnderecoEntity(List<EnderecoDTO> enderecoDTOS) {
        return enderecoDTOS.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO) {
        Endereco endereco = new Endereco();
        endereco.setRua(enderecoDTO.getRua());
        endereco.setNumero(Long.valueOf(enderecoDTO.getNumero()));
        endereco.setComplemento(enderecoDTO.getComplemento());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());
        endereco.setCep(enderecoDTO.getCep());
        return endereco;
    }

    public List<Telefone> paraListaTelefones(List<TelefoneDTO> telefoneDTOS) {
        return telefoneDTOS.stream().map(this::paraTelefoneEntity).toList();
    }

    public Telefone paraTelefoneEntity(TelefoneDTO telefoneDTO) {
        Telefone telefone = new Telefone();
        telefone.setNumero(telefoneDTO.getNumero());
        telefone.setDdd(telefoneDTO.getDdd());
        return telefone;
    }


    public UsuarioDTO paraUsuarioDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setSenha(usuario.getSenha());

        dto.setEnderecos(
                usuario.getEnderecos() != null
                        ? paraListaEnderecoDTO(usuario.getEnderecos())
                        : null
        );

        dto.setTelefones(
                usuario.getTelefones() != null
                        ? paraListaTelefoneDTO(usuario.getTelefones())
                        : null
        );

        return dto;
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecos) {
        return enderecos.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco) {
        EnderecoDTO dto = new EnderecoDTO();
        dto.setId(endereco.getId());
        dto.setRua(endereco.getRua());
        dto.setNumero(String.valueOf(endereco.getNumero())); // aqui é Long → String
        dto.setComplemento(endereco.getComplemento());
        dto.setCidade(endereco.getCidade());
        dto.setEstado(endereco.getEstado());
        dto.setCep(endereco.getCep());
        return dto;
    }

    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefones) {
        return telefones.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone) {
        TelefoneDTO dto = new TelefoneDTO();
        dto.setId(telefone.getId());
        dto.setNumero(telefone.getNumero());
        dto.setDdd(telefone.getDdd());
        return dto;
    }

    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario entity) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome());
        usuario.setId(usuario.getId() != null ? usuario.getId() : entity.getId());
        usuario.setSenha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : entity.getSenha());
        usuario.setEmail(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail());
        usuario.setEnderecos(entity.getEnderecos());
        usuario.setTelefones(entity.getTelefones());
        return usuario;
    }

    public Endereco updateEndereco(EnderecoDTO enderecoDTO, Endereco entity) {
        Endereco endereco = new Endereco();
        endereco.setId(enderecoDTO.getId() != null ? enderecoDTO.getId() : entity.getId());
        endereco.setRua(enderecoDTO.getRua() != null ? enderecoDTO.getRua() : entity.getRua());
        endereco.setNumero(enderecoDTO.getNumero() != null ? Long.valueOf(enderecoDTO.getNumero()) : entity.getNumero());
        endereco.setComplemento(enderecoDTO.getComplemento() != null ? enderecoDTO.getComplemento() : entity.getComplemento());
        endereco.setCidade(enderecoDTO.getCidade() != null ? enderecoDTO.getCidade() : entity.getCidade());
        endereco.setEstado(enderecoDTO.getEstado() != null ? enderecoDTO.getEstado() : entity.getEstado());
        endereco.setCep(enderecoDTO.getCep() != null ? enderecoDTO.getCep() : entity.getCep());
        return endereco;

    }

    public Telefone updateTelefone(TelefoneDTO telefoneDTO, Telefone entity){
        Telefone telefone =  new Telefone();
        telefone.setId(entity.getId());
        telefone.setDdd(telefoneDTO.getDdd() != null ? telefoneDTO.getDdd() : entity.getDdd());
        telefone.setNumero(telefoneDTO.getNumero() != null ? telefoneDTO.getNumero() : entity.getNumero());
        return telefone;
    }

    public Endereco paraEnderecoEntity(EnderecoDTO dto, Long idUsuario){
        Endereco endereco = new Endereco();
        endereco.setRua(dto.getRua());
        endereco.setCidade(dto.getCidade());
        endereco.setCep(dto.getCep());
        endereco.setComplemento(dto.getComplemento());
        endereco.setEstado(dto.getEstado());
        endereco.setNumero(Long.valueOf(dto.getNumero()));
        endereco.setUsuario_id(idUsuario);
        return endereco;
    }

    public Telefone paraTelefoneEntity(TelefoneDTO dto, Long idUsuario){
        Telefone telefone = new Telefone();
        telefone.setNumero(dto.getNumero());
        telefone.setDdd(dto.getDdd());
        telefone.setUsuario_id(idUsuario);
        return telefone;
    }

}