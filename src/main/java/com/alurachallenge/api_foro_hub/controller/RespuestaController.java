package com.alurachallenge.api_foro_hub.controller;



import com.alurachallenge.api_foro_hub.domain.respuesta.DatosActualizarRespuesta;
import com.alurachallenge.api_foro_hub.domain.respuesta.DatosDetalleRespuesta;
import com.alurachallenge.api_foro_hub.domain.respuesta.DatosRegistroRespuesta;
import com.alurachallenge.api_foro_hub.domain.respuesta.RespuestaRepository;
import com.alurachallenge.api_foro_hub.service.RespuestaService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@Valid @RequestBody DatosRegistroRespuesta datos, UriComponentsBuilder uriComponentsBuilder){
        var respuesta = respuestaService.registrar(datos);
        var uri = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.id()).toUri();
        return ResponseEntity.created(uri).body(respuesta);
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetalleRespuesta>> mostrar(@Parameter(hidden = true) @PageableDefault(size = 5, sort = "fechaCreacion") Pageable pag){
        var page = respuestaRepository.findAll(pag).map(DatosDetalleRespuesta::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity verRespuestaPorId(@PathVariable Long id){
        return respuestaRepository.findById(id)
                .map(r -> ResponseEntity.ok(new DatosDetalleRespuesta(r)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarRespuesta(@PathVariable Long id, @Valid @RequestBody DatosActualizarRespuesta datos){
        var respuesta = respuestaService.actualizar(id, datos);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarRespuesta(@PathVariable Long id){
        respuestaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
