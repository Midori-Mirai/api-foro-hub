package com.alurachallenge.api_foro_hub.Infra.security;

import com.alurachallenge.api_foro_hub.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {
    private static final String ISSUER = "API Foro.hub";
    @Value("${api.security.token.secret}")/*spring framework*/
    private String secret;

    @Value("${api.security.token.expiration}")
    private Long tiempoExpiracion;

    public String generarToken(Usuario usuario){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withSubject(usuario.getEmail())
                .withExpiresAt(fechaExpiracion())
                .withIssuer(ISSUER)
                .sign(algorithm);
        return token;
    }

    private Instant fechaExpiracion() {
        return LocalDateTime.now()
                .plusSeconds(tiempoExpiracion)
                .toInstant(ZoneOffset.of("-06:00"));
    }

    public String getSubject(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("API Foro.hub") // debe coincidir con el issuer usado al generar el token
                    .build()
                    .verify(token) // si el token es inválido o expiró, lanza excepción
                    .getSubject(); // extrae el "subject" (usualmente el email o username)
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token JWT no válido o expirado");
        }
    }

}
