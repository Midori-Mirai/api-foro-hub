package com.alurachallenge.api_foro_hub.domain.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRegistroRespuesta(
        @NotBlank(message = "{mensaje.obligatorio}")
        String mensaje,

        @NotNull Long autorId,
        @NotNull Long topicoId
) {
}
