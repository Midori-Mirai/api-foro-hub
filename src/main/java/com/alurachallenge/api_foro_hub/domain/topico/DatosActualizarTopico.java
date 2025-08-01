package com.alurachallenge.api_foro_hub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
        @NotBlank(message = "{titulo.obligatorio}")
        String titulo,

        @NotBlank(message = "{mensaje.obligatorio}")
        String mensaje,

        @NotNull
        Long idCurso
) {
    public DatosActualizarTopico(Topico topico){
        this(
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getCurso().getId()
        );
    }
}
