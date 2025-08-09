package com.alurachallenge.api_foro_hub.service;

import com.alurachallenge.api_foro_hub.domain.usuario.DatosDetalleUsuario;
import com.alurachallenge.api_foro_hub.domain.usuario.DatosRegistroUsuario;
import com.alurachallenge.api_foro_hub.domain.usuario.Usuario;
import com.alurachallenge.api_foro_hub.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public DatosDetalleUsuario registrar(@Valid DatosRegistroUsuario datos){
        String passwordCifrada = passwordEncoder.encode(datos.contrasena());
        var usuario = new Usuario(null, datos.nombre(), datos.email(), passwordCifrada);
        Usuario usuarioRegistrado = usuarioRepository.save(usuario);
        return new DatosDetalleUsuario(usuario);
    }

    public Page<DatosDetalleUsuario> mostrar(Pageable pag) {
        return usuarioRepository.findAll(pag).map(DatosDetalleUsuario::new);
    }

    public List<DatosDetalleUsuario> buscar(String nombre) {
        return usuarioRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(DatosDetalleUsuario::new)
                .collect(Collectors.toList());
    }
}
