package com.alurachallenge.api_foro_hub.Infra.exception;

import com.alurachallenge.api_foro_hub.domain.ValidacionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GestorDeErrores {
    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity tratarErrorDeValidacion(ValidacionException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
