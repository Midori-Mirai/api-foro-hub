package com.alurachallenge.api_foro_hub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosRegistroUsuario(
        @NotBlank(message = "{nombre.obligatorio}")
        String nombre,

        @NotBlank(message = "{email.obligatorio}")
        @Email(message = "{email.invalido}")
        String email,

        @NotBlank(message = "{pass.obligatorio}")
        String contrasena
) {
}
