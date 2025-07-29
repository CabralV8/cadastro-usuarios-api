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
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            UsuarioConverter usuarioConverter,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioConverter = usuarioConverter;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioConverter.paraUsuarioEntity(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

}
