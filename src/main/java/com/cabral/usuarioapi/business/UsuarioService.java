package com.cabral.usuarioapi.business;

import com.cabral.usuarioapi.business.converter.UsuarioConverter;
import com.cabral.usuarioapi.business.dto.EnderecoDTO;
import com.cabral.usuarioapi.business.dto.TelefoneDTO;
import com.cabral.usuarioapi.business.dto.UsuarioDTO;
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

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuarioEntity(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
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

    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        try {
            return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Email não encontrado" + email)));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Email não encontrado" + email);
        }
    }
    public void deletarUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizarDadosUsuario(String token, UsuarioDTO usuarioDTO){
        // Aqui é realizada a busca do usuário através do token
        String email = jwtUtil.extractEmailToken(token.substring(7));

        // Crptografia de senha.
        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null );

        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(()->
                new ResourceNotFoundException("Email não localizado."));

        Usuario usuario = usuarioConverter.updateUsuario(usuarioDTO, usuarioEntity);


        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizaEndereco(String idEndereco, EnderecoDTO enderecoDTO){

        Endereco entity = enderecoRepository.findById(Long.valueOf(idEndereco)).orElseThrow(
                ()-> new ResourceNotFoundException("Id não encontrado" + idEndereco));

        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO dto){

        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(
                ()-> new ResourceNotFoundException("Id não encontrado" + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(dto, entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

    public EnderecoDTO cadastraEndereco(String token, EnderecoDTO dto) {
        String email = jwtUtil.extractEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado " + email));

        Endereco endereco = usuarioConverter.paraEnderecoEntity(dto, usuario.getId());
        Endereco enderecoEntity = enderecoRepository.save(endereco);
        return usuarioConverter.paraEnderecoDTO(enderecoEntity);
    }

    public TelefoneDTO cadastraTelefone(String token, TelefoneDTO dto){
        String email = jwtUtil.extractEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(()->
                new ResourceNotFoundException("Email não localizado " + email));

        Telefone telefone = usuarioConverter.paraTelefoneEntity(dto, usuario.getId());
        Telefone telefoneEntity = telefoneRepository.save(telefone);
        return usuarioConverter.paraTelefoneDTO(telefoneEntity);
    }

}
