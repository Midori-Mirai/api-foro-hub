package com.alurachallenge.api_foro_hub.domain.respuesta;

import java.time.LocalDateTime;

public record DatosDetalleRespuesta(
        Long id,
        String nombreUsuario,
        String nombreTopico,
        String mensaje,
        Boolean solucion,
        LocalDateTime fecha
) {
    public DatosDetalleRespuesta(Respuesta respuesta) {
        this(
                respuesta.getId(),
                respuesta.getAutor().getNombre(),
                respuesta.getTopico().getTitulo(),
                respuesta.getMensaje(),
                respuesta.getSolucion(),
                respuesta.getFechaCreacion()
        );
    }
}
