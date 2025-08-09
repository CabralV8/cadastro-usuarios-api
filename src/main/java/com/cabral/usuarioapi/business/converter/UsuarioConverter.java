package com.cabral.usuarioapi.business.converter;

import com.cabral.usuarioapi.business.dto.EnderecoDTORecord;
import com.cabral.usuarioapi.business.dto.TeleFoneDTORecord;
import com.cabral.usuarioapi.business.dto.UsuarioDTORecord;
import com.cabral.usuarioapi.insfrastructure.entity.Endereco;
import com.cabral.usuarioapi.insfrastructure.entity.Telefone;
import com.cabral.usuarioapi.insfrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuarioEntity(UsuarioDTORecord usuarioDTO, String senhaCriptografada) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.nome());
        usuario.setEmail(usuarioDTO.email());
        usuario.setSenha(senhaCriptografada);

        usuario.setEnderecos(
                usuarioDTO.enderecos() != null
                        ? paraListaEnderecoEntity(usuarioDTO.enderecos())
                        : null
        );

        usuario.setTelefones(
                usuarioDTO.telefones() != null
                        ? paraListaTelefones(usuarioDTO.telefones())
                        : null
        );

        return usuario;
    }


    public List<Endereco> paraListaEnderecoEntity(List<EnderecoDTORecord> enderecoDTOS) {
        return enderecoDTOS.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDTORecord enderecoDTORecord) {
        Endereco endereco = new Endereco();
        endereco.setRua(enderecoDTORecord.rua());
        endereco.setNumero(Long.valueOf(enderecoDTORecord.numero()));
        endereco.setComplemento(enderecoDTORecord.complemento());
        endereco.setCidade(enderecoDTORecord.cidade());
        endereco.setEstado(enderecoDTORecord.estado());
        endereco.setCep(enderecoDTORecord.cep());
        return endereco;
    }

    public List<Telefone> paraListaTelefones(List<TeleFoneDTORecord> telefoneDTOS) {
        return telefoneDTOS.stream().map(this::paraTelefoneEntity).toList();
    }

    public Telefone paraTelefoneEntity(TeleFoneDTORecord telefoneDTO) {
        Telefone telefone = new Telefone();
        telefone.setNumero(telefoneDTO.numero());
        telefone.setDdd(telefoneDTO.ddd());
        return telefone;
    }


    public UsuarioDTORecord paraUsuarioDTORecord(Usuario usuario) {
        return new UsuarioDTORecord(
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getEnderecos() != null
                        ? paraListaEnderecoDTO(usuario.getEnderecos())
                        : null,
                usuario.getTelefones() != null
                        ? paraListaTelefoneDTORecord(usuario.getTelefones())
                        : null
        );
    }

    public List<EnderecoDTORecord> paraListaEnderecoDTO(List<Endereco> enderecos) {
        return enderecos.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTORecord paraEnderecoDTO(Endereco endereco) {
        return new EnderecoDTORecord(
                endereco.getId(),
                endereco.getRua(),
                String.valueOf(endereco.getNumero()),
                endereco.getComplemento(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep()
        );
    }


    public List<TeleFoneDTORecord> paraListaTelefoneDTORecord(List<Telefone> telefones) {
        return telefones.stream().map(this::paraTelefoneDTORecord).toList();
    }

    public TeleFoneDTORecord paraTelefoneDTORecord(Telefone telefone) {
        return new TeleFoneDTORecord(
                telefone.getId(),
                telefone.getNumero(),
                telefone.getDdd()
        );
    }

    public Usuario updateUsuario(UsuarioDTORecord usuarioDTO, Usuario entity, String senhaCriptografada) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.nome() != null ? usuarioDTO.nome() : entity.getNome());
        usuario.setId(entity.getId() != null ? usuario.getId() : entity.getId());
        usuario.setSenha(usuarioDTO.senha() != null ? usuarioDTO.senha() : entity.getSenha());
        usuario.setEmail(usuarioDTO.email() != null ? usuarioDTO.email() : entity.getEmail());
        usuario.setEnderecos(entity.getEnderecos());
        usuario.setTelefones(entity.getTelefones());
        return usuario;
    }

    public Endereco updateEndereco(EnderecoDTORecord enderecoDTO, Endereco entity) {
        Endereco endereco = new Endereco();
        endereco.setId(enderecoDTO.id() != null ? enderecoDTO.id() : entity.getId());
        endereco.setRua(enderecoDTO.rua() != null ? enderecoDTO.rua() : entity.getRua());
        endereco.setNumero(enderecoDTO.numero() != null ? Long.valueOf(enderecoDTO.numero()) : entity.getNumero());
        endereco.setComplemento(enderecoDTO.complemento() != null ? enderecoDTO.complemento() : entity.getComplemento());
        endereco.setCidade(enderecoDTO.cidade() != null ? enderecoDTO.cidade() : entity.getCidade());
        endereco.setEstado(enderecoDTO.estado() != null ? enderecoDTO.estado() : entity.getEstado());
        endereco.setCep(enderecoDTO.cep() != null ? enderecoDTO.cep() : entity.getCep());
        return endereco;

    }

    public Telefone updateTelefone(TeleFoneDTORecord telefoneDTO, Telefone entity) {
        Telefone telefone = new Telefone();
        telefone.setId(entity.getId());
        telefone.setDdd(telefoneDTO.ddd() != null ? telefoneDTO.ddd() : entity.getDdd());
        telefone.setNumero(telefoneDTO.numero() != null ? telefoneDTO.numero() : entity.getNumero());
        return telefone;
    }

    public Endereco paraEnderecoEntity(EnderecoDTORecord dto, Long idUsuario) {
        Endereco endereco = new Endereco();
        endereco.setRua(dto.rua());
        endereco.setCidade(dto.cidade());
        endereco.setCep(dto.cep());
        endereco.setComplemento(dto.complemento());
        endereco.setEstado(dto.estado());
        endereco.setNumero(Long.valueOf(dto.numero()));
        endereco.setUsuario_id(idUsuario);
        return endereco;
    }

    public Telefone paraTelefoneEntity(TeleFoneDTORecord dto, Long idUsuario) {
        Telefone telefone = new Telefone();
        telefone.setNumero(dto.numero());
        telefone.setDdd(dto.ddd());
        telefone.setUsuario_id(idUsuario);
        return telefone;
    }

}