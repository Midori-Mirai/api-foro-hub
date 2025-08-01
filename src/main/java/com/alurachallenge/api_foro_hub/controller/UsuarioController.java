package com.alurachallenge.api_foro_hub.controller;

import com.alurachallenge.api_foro_hub.domain.curso.DatosDetalleCurso;
import com.alurachallenge.api_foro_hub.domain.usuario.DatosDetalleUsuario;
import com.alurachallenge.api_foro_hub.domain.usuario.DatosRegistroUsuario;
import com.alurachallenge.api_foro_hub.domain.usuario.Usuario;
import com.alurachallenge.api_foro_hub.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetalleUsuario> registrar(@Valid @RequestBody DatosRegistroUsuario datos, UriComponentsBuilder uriComponentsBuilder) {
        String passwordCifrada = passwordEncoder.encode(datos.contrasena());
        var usuario = new Usuario(null, datos.nombre(), datos.email(), passwordCifrada);
        Usuario usuarioRegistrado = usuarioRepository.save(usuario);
        var uri = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetalleUsuario(usuario));
    }

}
