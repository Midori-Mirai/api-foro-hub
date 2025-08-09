package com.alurachallenge.api_foro_hub.Infra.security;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta del login con el token JWT")
public record DatosTokenJWT(
        @Schema(description = "Token JWT", example = "eyJhbGciOiJIUzI1NiIs...")
        String tokenJWT) {
}
