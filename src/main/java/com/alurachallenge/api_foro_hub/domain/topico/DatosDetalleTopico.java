package com.alurachallenge.api_foro_hub.domain.topico;

import com.alurachallenge.api_foro_hub.domain.curso.Categoria;
import com.alurachallenge.api_foro_hub.domain.usuario.Usuario;

import java.time.LocalDateTime;

public record DatosDetalleTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        StatusTopico status,
        String nombreUsuario,
        String nombreCurso,
        Categoria categoria
) {
    public DatosDetalleTopico(Topico topico){
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre(),
                topico.getCurso().getCategoria()
        );
    }
}
