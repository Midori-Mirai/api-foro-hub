package com.alurachallenge.api_foro_hub.controller;

import com.alurachallenge.api_foro_hub.domain.ValidacionException;
import com.alurachallenge.api_foro_hub.domain.curso.CursoRepository;
import com.alurachallenge.api_foro_hub.domain.topico.*;
import com.alurachallenge.api_foro_hub.domain.usuario.UsuarioRepository;
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

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    //Create
    @PostMapping
    @Transactional
    public ResponseEntity registrar(@Valid @RequestBody DatosRegistroTopico datos, UriComponentsBuilder uriComponentsBuilder){
        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            return ResponseEntity.badRequest().body("Ya existe un tópico con el mismo título y mensaje");
        }
        var autor = usuarioRepository.getReferenceById(datos.idUsuario());
        var curso = cursoRepository.getReferenceById(datos.idCurso());
        var topico = new Topico(datos, autor, curso);
        topicoRepository.save(topico);
        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetalleTopico(topico));
    }

    //Read
    @GetMapping
    public ResponseEntity<Page<DatosDetalleTopico>> mostrar (@PageableDefault(size = 5, sort = "fechaCreacion") Pageable paginacion){
        var page = topicoRepository.findAll(paginacion).map(DatosDetalleTopico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<DatosDetalleTopico>> buscarPorCursoYAnio(
            /*@RequestParam porque año no es un atributo y sino Spring JPA no lo identifica*/
            @RequestParam String nombreCurso, @RequestParam int anio){
        var resultados = topicoRepository.findByCursoNombreAndAnio(nombreCurso, anio)
                .stream()
                .map(DatosDetalleTopico::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultados);
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
        return topicoRepository.findById(id)
                .map(t -> {//verificar si el tópico no se esta duplicando
                    if(topicoRepository.existsByTituloAndMensajeAndIdNot(datos.titulo(), datos.mensaje(), id)){
                        throw new ValidacionException("Ya existe otro tópico con el mismo título y mensaje");
                    }
                    //buscar el id del curso y setear los valores
                    var curso = cursoRepository.getReferenceById(datos.idCurso());
                    t.setTitulo(datos.titulo());
                    t.setMensaje(datos.mensaje());
                    t.setCurso(curso);
                    return ResponseEntity.ok(new DatosDetalleTopico(t));
                })
                .orElseThrow(()-> new ValidacionException("No existe un tópico con ese id"));
    }

    //Delete
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        if(!topicoRepository.findById(id).isPresent()){
            throw new ValidacionException("El id del topico que deseas eliminar no existe");
        }
        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
