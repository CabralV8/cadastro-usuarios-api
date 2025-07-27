package com.cabral.usuarioapi.controllers;

import com.cabral.usuarioapi.business.UsuarioService;
import com.cabral.usuarioapi.business.dto.UsuarioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }
}
