package com.alurachallenge.api_foro_hub.controller;


import com.alurachallenge.api_foro_hub.domain.usuario.DatosDetalleUsuario;
import com.alurachallenge.api_foro_hub.domain.usuario.DatosRegistroUsuario;

import com.alurachallenge.api_foro_hub.domain.usuario.UsuarioRepository;
import com.alurachallenge.api_foro_hub.service.UsuarioService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetalleUsuario> registrar(@Valid @RequestBody DatosRegistroUsuario datos, UriComponentsBuilder uriComponentsBuilder) {
        var usuario = usuarioService.registrar(datos);
        var uri = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.id()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetalleUsuario>> mostrar(@Parameter(hidden = true) @PageableDefault(size = 5, sort = "nombre")Pageable pag){
        var page = usuarioService.mostrar(pag);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<DatosDetalleUsuario>> buscarPorNombre(@RequestParam String nombre){
        var resultado = usuarioService.buscar(nombre);
        return ResponseEntity.ok(resultado);
    }

}
