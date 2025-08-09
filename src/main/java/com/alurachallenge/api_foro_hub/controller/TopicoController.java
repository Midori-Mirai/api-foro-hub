package com.alurachallenge.api_foro_hub.controller;

import com.alurachallenge.api_foro_hub.domain.ValidacionException;
import com.alurachallenge.api_foro_hub.domain.curso.CursoRepository;
import com.alurachallenge.api_foro_hub.domain.topico.*;
import com.alurachallenge.api_foro_hub.domain.usuario.UsuarioRepository;
import com.alurachallenge.api_foro_hub.service.TopicoService;
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

import java.util.List;
import java.util.stream.Collectors;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TopicoService topicoService;

    //Create
    @PostMapping
    @Transactional
    public ResponseEntity registrar(@Valid @RequestBody DatosRegistroTopico datos, UriComponentsBuilder uriComponentsBuilder){
        var topico = topicoService.registrar(datos);
        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.id()).toUri();
        return ResponseEntity.created(uri).body(topico);
    }

    //Read
    @GetMapping
    public ResponseEntity<Page<DatosDetalleTopico>> mostrar (@PageableDefault(size = 5, sort = "fechaCreacion") Pageable paginacion){
        var page = topicoService.mostrar(paginacion);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<DatosDetalleTopico>> buscarPorCursoYAnio(
            /*@RequestParam porque año no es un atributo y sino Spring JPA no lo identifica*/
            @RequestParam String nombreCurso, @RequestParam int anio){
        var resultado = topicoService.buscar(nombreCurso, anio);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity verTopicoPorId(@PathVariable Long id){
        /*var topico = topicoRepository.getReferenceById(id);
        var topicoDTO = new DatosDetalleTopico(topico);
        return ResponseEntity.ok(topicoDTO);*/
        return topicoRepository.findById(id)
                .map(t -> ResponseEntity.ok(new DatosDetalleTopico(t)))
                .orElse(ResponseEntity.notFound().build());
    }

    //Update
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datos){
        var topico = topicoService.actualizar(id, datos);
        return ResponseEntity.ok(topico);
    }

    //Delete
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        topicoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
