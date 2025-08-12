package com.cabral.usuarioapi.controllers;

import com.cabral.usuarioapi.business.UsuarioService;
import com.cabral.usuarioapi.business.ViaCepService;
import com.cabral.usuarioapi.business.dto.EnderecoDTORecord;
import com.cabral.usuarioapi.business.dto.TeleFoneDTORecord;
import com.cabral.usuarioapi.business.dto.UsuarioDTORecord;
import com.cabral.usuarioapi.insfrastructure.clients.ViaCepRecord;
import com.cabral.usuarioapi.insfrastructure.security.JwtUtil;
import com.cabral.usuarioapi.insfrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
@Tag(name = "Usuários", description = "Gerenciamento de usuários, endereços e telefones")
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
    @Operation(summary = "Cadastrar usuário", description = "Registra um novo usuário no sistema.")
    @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso.")
    @ApiResponse(responseCode = "409", description = "Usuário já existe.")
    public ResponseEntity<UsuarioDTORecord> salvaUsuario(@RequestBody UsuarioDTORecord usuarioDTO){
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Autentica o usuário e retorna um token JWT.")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso.")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas.")
    public String login(@RequestBody UsuarioDTORecord usuarioDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.email(),
                        usuarioDTO.senha()));
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    @Operation(summary = "Buscar usuário por e-mail", description = "Retorna os dados do usuário através do e-mail informado.")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado.")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    public ResponseEntity<UsuarioDTORecord> buscarUsuarioPorEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "Deletar usuário", description = "Remove um usuário pelo e-mail.")
    @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    public ResponseEntity<Void> deletarUsuarioPorEmail(@PathVariable String email){
        usuarioService.deletarUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(summary = "Atualizar dados do usuário", description = "Atualiza os dados de um usuário autenticado.")
    @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso.")
    @ApiResponse(responseCode = "401", description = "Não autorizado.")
    public ResponseEntity<UsuarioDTORecord> atualizaDadoUsuario(@RequestBody UsuarioDTORecord dto,
                                                                @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizarDadosUsuario(token, dto));
    }

    @PutMapping("/endereco")
    @Operation(summary = "Atualizar endereço", description = "Atualiza um endereço existente pelo ID.")
    @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Endereço não encontrado.")
    public ResponseEntity<EnderecoDTORecord> atualizaEndereco(@RequestBody EnderecoDTORecord dto,
                                                              @RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioService.atualizaEndereco(String.valueOf(id), dto));
    }

    @PutMapping("/telefone")
    @Operation(summary = "Atualizar telefone", description = "Atualiza um telefone existente pelo ID.")
    @ApiResponse(responseCode = "200", description = "Telefone atualizado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Telefone não encontrado.")
    public ResponseEntity<TeleFoneDTORecord> atualizaTelefone(@RequestBody TeleFoneDTORecord dto,
                                                              @RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioService.atualizaTelefone(id, dto));
    }

    @PostMapping("/endereco")
    @Operation(summary = "Cadastrar endereço", description = "Adiciona um novo endereço ao usuário autenticado.")
    @ApiResponse(responseCode = "200", description = "Endereço cadastrado com sucesso.")
    @ApiResponse(responseCode = "401", description = "Não autorizado.")
    public ResponseEntity<EnderecoDTORecord> cadastraEndereco(@RequestBody EnderecoDTORecord dto,
                                                              @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastraEndereco(token, dto));
    }

    @PostMapping("/telefone")
    @Operation(summary = "Cadastrar telefone", description = "Registra um novo telefone para o usuário autenticado.")
    @ApiResponse(responseCode = "200", description = "Telefone cadastrado com sucesso.")
    @ApiResponse(responseCode = "401", description = "Não autorizado.")
    public ResponseEntity<TeleFoneDTORecord> cadastraTelefone(@RequestBody TeleFoneDTORecord dto,
                                                              @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.cadastraTelefone(token, dto));
    }

    @GetMapping("/endereco/{cep}")
    @Operation(summary = "Buscar endereço por CEP", description = "Consulta o ViaCEP para buscar dados de endereço pelo CEP informado.")
    @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso.")
    @ApiResponse(responseCode = "400", description = "CEP inválido.")
    public ResponseEntity<ViaCepRecord> buscarDadosCep(@PathVariable("cep")String cep){
        return ResponseEntity.ok(viaCepService.buscarDadosEndereco(cep));
    }
}

