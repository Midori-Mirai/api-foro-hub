package com.alurachallenge.api_foro_hub.service;

import ch.qos.logback.classic.spi.IThrowableProxy;
import com.alurachallenge.api_foro_hub.domain.ValidacionException;
import com.alurachallenge.api_foro_hub.domain.curso.CursoRepository;
import com.alurachallenge.api_foro_hub.domain.respuesta.DatosActualizarRespuesta;
import com.alurachallenge.api_foro_hub.domain.topico.*;
import com.alurachallenge.api_foro_hub.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicoService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    public DatosDetalleTopico registrar(@Valid DatosRegistroTopico datos){
        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            throw new ValidacionException("Ya existe un tópico con el mismo título y mensaje");
        }
        var autor = usuarioRepository.getReferenceById(datos.idUsuario());
        var curso = cursoRepository.getReferenceById(datos.idCurso());
        var topico = new Topico(datos, autor, curso);
        topicoRepository.save(topico);
        return new DatosDetalleTopico(topico);
    }

    public Page<DatosDetalleTopico> mostrar(Pageable pag){
        return topicoRepository.findAll(pag).map(DatosDetalleTopico::new);
    }

    public List<DatosDetalleTopico> buscar(String nombreCurso, int anio){
        return topicoRepository.findByCursoNombreAndAnio(nombreCurso, anio)
                .stream()
                .map(DatosDetalleTopico::new)
                .collect(Collectors.toList());
    }

    public DatosDetalleTopico actualizar(Long id, @Valid DatosActualizarTopico datos){
        return topicoRepository.findById(id)
                .map(t -> {//verificar si el tópico no se esta duplicando
                    if(topicoRepository.existsByTituloAndMensajeAndIdNot(datos.titulo(), datos.mensaje(), id)){
                        throw new ValidacionException("Ya existe otro tópico con el mismo título y mensaje");
                    }
                    //buscar el id del curso y settear los valores
                    var curso = cursoRepository.getReferenceById(datos.idCurso());
                    t.setTitulo(datos.titulo());
                    t.setMensaje(datos.mensaje());
                    t.setCurso(curso);
                    return new DatosDetalleTopico(t);
                })
                .orElseThrow(()-> new ValidacionException("No existe un tópico con ese id"));
    }

    public void eliminar(Long id){
        if(!topicoRepository.findById(id).isPresent()){
            throw new ValidacionException("El id del topico que deseas eliminar no existe");
        }
        topicoRepository.deleteById(id);
    }
}
