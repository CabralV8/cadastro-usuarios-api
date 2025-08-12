package com.cabral.usuarioapi.business;

import com.cabral.usuarioapi.business.converter.UsuarioConverter;
import com.cabral.usuarioapi.business.dto.EnderecoDTORecord;
import com.cabral.usuarioapi.business.dto.TeleFoneDTORecord;
import com.cabral.usuarioapi.business.dto.UsuarioDTORecord;
import com.cabral.usuarioapi.insfrastructure.entity.Endereco;
import com.cabral.usuarioapi.insfrastructure.entity.Telefone;
import com.cabral.usuarioapi.insfrastructure.entity.Usuario;
import com.cabral.usuarioapi.insfrastructure.exceptions.ConflictException;
import com.cabral.usuarioapi.insfrastructure.exceptions.ResourceNotFoundException;
import com.cabral.usuarioapi.insfrastructure.repository.EnderecoRepository;
import com.cabral.usuarioapi.insfrastructure.repository.TelefoneRepository;
import com.cabral.usuarioapi.insfrastructure.repository.UsuarioRepository;
import com.cabral.usuarioapi.insfrastructure.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            UsuarioConverter usuarioConverter,
            PasswordEncoder passwordEncoder, JwtUtil jwtUtil, EnderecoRepository enderecoRepository, TelefoneRepository telefoneRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioConverter = usuarioConverter;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.enderecoRepository = enderecoRepository;
        this.telefoneRepository = telefoneRepository;
    }

    public UsuarioDTORecord salvaUsuario(UsuarioDTORecord usuarioDTO) {
        emailExiste(usuarioDTO.email());
        String senhaCriptografada = passwordEncoder.encode(usuarioDTO.senha());
        Usuario usuario = usuarioConverter.paraUsuarioEntity(usuarioDTO, senhaCriptografada);
        return usuarioConverter.paraUsuarioDTORecord(
                usuarioRepository.save(usuario)
        );
    }


    public void emailExiste(String email) {
        try {
            boolean existe = verificarEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado" + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado" + e.getCause());
        }
    }

    public boolean verificarEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTORecord buscarUsuarioPorEmail(String email) {
        try {
            return usuarioConverter.paraUsuarioDTORecord(usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Email não encontrado" + email)));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Email não encontrado" + email);
        }
    }
    public void deletarUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTORecord atualizarDadosUsuario(String token, UsuarioDTORecord usuarioDTO) {
        // Extrai o e-mail do token
        String email = jwtUtil.extractEmailToken(token.substring(7));

        // Busca o usuário no banco
        Usuario usuarioEntity = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email não localizado."));

        // Criptografa a senha (se ela não for nula)
        String senhaCriptografada = usuarioDTO.senha() != null
                ? passwordEncoder.encode(usuarioDTO.senha())
                : usuarioEntity.getSenha(); // Mantém a senha antiga se não for informada

        // Atualiza o usuário com os dados do DTO e a nova senha (ou a antiga se não foi enviada)
        Usuario usuarioAtualizado = usuarioConverter.updateUsuario(usuarioDTO, usuarioEntity, senhaCriptografada);

        // Salva no banco e retorna o DTO atualizado
        return usuarioConverter.paraUsuarioDTORecord(usuarioRepository.save(usuarioAtualizado));
    }

    public EnderecoDTORecord atualizaEndereco(String idEndereco, EnderecoDTORecord enderecoDTO){

        Endereco entity = enderecoRepository.findById(Long.valueOf(idEndereco)).orElseThrow(
                ()-> new ResourceNotFoundException("Id não encontrado" + idEndereco));

        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TeleFoneDTORecord atualizaTelefone(Long idTelefone, TeleFoneDTORecord dto){

        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(
                ()-> new ResourceNotFoundException("Id não encontrado" + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(dto, entity);

        return usuarioConverter.paraTelefoneDTORecord(telefoneRepository.save(telefone));
    }

    public EnderecoDTORecord cadastraEndereco(String token, EnderecoDTORecord dto) {
        String email = jwtUtil.extractEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado " + email));

        Endereco endereco = usuarioConverter.paraEnderecoEntity(dto, usuario.getId());
        Endereco enderecoEntity = enderecoRepository.save(endereco);
        return usuarioConverter.paraEnderecoDTO(enderecoEntity);
    }

    public TeleFoneDTORecord cadastraTelefone(String token, TeleFoneDTORecord dto){
        String email = jwtUtil.extractEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(()->
                new ResourceNotFoundException("Email não localizado " + email));

        Telefone telefone = usuarioConverter.paraTelefoneEntity(dto, usuario.getId());
        Telefone telefoneEntity = telefoneRepository.save(telefone);
        return usuarioConverter.paraTelefoneDTORecord(telefoneEntity);
    }

}
