package com.cabral.usuarioapi.controllers;

import com.cabral.usuarioapi.business.UsuarioService;
import com.cabral.usuarioapi.business.ViaCepService;
import com.cabral.usuarioapi.business.dto.EnderecoDTORecord;
import com.cabral.usuarioapi.business.dto.TeleFoneDTORecord;
import com.cabral.usuarioapi.business.dto.UsuarioDTORecord;
import com.cabral.usuarioapi.insfrastructure.clients.ViaCepRecord;
import com.cabral.usuarioapi.insfrastructure.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ViaCepService viaCepService;

    public UsuarioController(UsuarioService usuarioService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, ViaCepService viaCepService) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.viaCepService = viaCepService;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTORecord> salvaUsuario(@RequestBody UsuarioDTORecord usuarioDTO){
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTORecord usuarioDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.email(),
                        usuarioDTO.senha()));

        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<UsuarioDTORecord> buscarUsuarioPorEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletarUsuarioPorEmail(@PathVariable String email){
        usuarioService.deletarUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UsuarioDTORecord> atualizaDadoUsuario(@RequestBody UsuarioDTORecord dto,
                                                      @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizarDadosUsuario(token, dto));
    }

    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTORecord> atualizaEndereco(@RequestBody EnderecoDTORecord dto,
                                                              @RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioService.atualizaEndereco(String.valueOf(id), dto));
    }

    @PutMapping("/telefone")
    public ResponseEntity<TeleFoneDTORecord> atualizaTelefone(@RequestBody TeleFoneDTORecord dto,
                                                              @RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioService.atualizaTelefone(id, dto));
    }

    @PostMapping("/endereco")
    public ResponseEntity<EnderecoDTORecord> cadastraEndereco(@RequestBody EnderecoDTORecord dto,
                                                        @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastraEndereco(token, dto));
    }
    @PostMapping("/telefone")
    public ResponseEntity<TeleFoneDTORecord> cadastraTelefone(@RequestBody TeleFoneDTORecord dto,
                                                        @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastraTelefone(token, dto));
    }

    @GetMapping("/endereco/{cep}")
    public ResponseEntity<ViaCepRecord> buscarDadosCep(@PathVariable("cep")String cep){
        return ResponseEntity.ok(viaCepService.buscarDadosEndereco(cep));
    }
}
