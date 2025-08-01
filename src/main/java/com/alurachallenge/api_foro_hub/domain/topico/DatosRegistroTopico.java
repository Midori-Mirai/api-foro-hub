package com.alurachallenge.api_foro_hub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRegistroTopico(
        @NotBlank(message = "{titulo.obligatorio}")
        String titulo,

        @NotBlank(message = "{mensaje.obligatorio}")
        String mensaje,

        @NotNull LocalDateTime fechaCreacion,
        @NotNull Long idUsuario,
        @NotNull Long idCurso
) {
}
