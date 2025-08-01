package com.alurachallenge.api_foro_hub.domain.usuario;

import com.alurachallenge.api_foro_hub.domain.curso.Curso;

public record DatosDetalleUsuario(
        Long id,
        String nombre
) {
    public DatosDetalleUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNombre()
        );
    }
}
