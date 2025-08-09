package com.alurachallenge.api_foro_hub.domain.respuesta;

import jakarta.validation.constraints.NotBlank;

public record DatosActualizarRespuesta(
        @NotBlank(message = "{mensaje.obligatorio}")
        String mensaje
) {
}
