package com.cabral.usuarioapi.business;

import com.cabral.usuarioapi.business.converter.UsuarioConverter;
import com.cabral.usuarioapi.business.dto.UsuarioDTO;
import com.cabral.usuarioapi.insfrastructure.entity.Usuario;
import com.cabral.usuarioapi.insfrastructure.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, UsuarioConverter usuarioConverter) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioConverter = usuarioConverter;
    }

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioConverter.paraUsuarioEntity(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }


}
