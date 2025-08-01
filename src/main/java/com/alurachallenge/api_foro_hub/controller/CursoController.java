package com.alurachallenge.api_foro_hub.controller;

import com.alurachallenge.api_foro_hub.domain.curso.Curso;
import com.alurachallenge.api_foro_hub.domain.curso.CursoRepository;
import com.alurachallenge.api_foro_hub.domain.curso.DatosDetalleCurso;
import com.alurachallenge.api_foro_hub.domain.curso.DatosRegistroCurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody DatosRegistroCurso datos, UriComponentsBuilder uriComponentsBuilder){
        if (cursoRepository.existsByNombre(datos.nombre())) {
            return ResponseEntity
                    .badRequest()
                    .body("Ya existe un curso con el nombre '" + datos.nombre() + "'");
        }
        var curso = new Curso(datos);
        cursoRepository.save(curso);
        var uri = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetalleCurso(curso));
    }

    /*@GetMapping
    public List<DatosDetalleCurso> listar(){
        List<DatosDetalleCurso> page = cursoRepository.findAll()
                .stream()
                .map(c -> new DatosDetalleCurso(c))
                .toList();
        return page;
    }*/
    @GetMapping
    public ResponseEntity<Page<DatosDetalleCurso>> mostrar(@PageableDefault(size = 15, sort ={"categoria"}) Pageable paginacion){
        var page = cursoRepository.findAll(paginacion).map(DatosDetalleCurso::new);
        return ResponseEntity.ok(page);
    }
}
