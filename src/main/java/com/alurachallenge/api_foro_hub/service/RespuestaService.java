package com.alurachallenge.api_foro_hub.service;

import com.alurachallenge.api_foro_hub.domain.ValidacionException;
import com.alurachallenge.api_foro_hub.domain.respuesta.*;
import com.alurachallenge.api_foro_hub.domain.topico.TopicoRepository;
import com.alurachallenge.api_foro_hub.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class RespuestaService {
    @Autowired
    TopicoRepository topicoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RespuestaRepository respuestaRepository;

    public DatosDetalleRespuesta registrar(@Valid DatosRegistroRespuesta datos){
        var autor = usuarioRepository.getReferenceById(datos.autorId());
        var topico = topicoRepository.getReferenceById(datos.topicoId());
        var respuesta = new Respuesta(datos, autor, topico);
        respuestaRepository.save(respuesta);
//        var uri = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return new DatosDetalleRespuesta(respuesta);
    }

    public DatosDetalleRespuesta actualizar(Long id, @Valid DatosActualizarRespuesta datos){
        return respuestaRepository.findById(id)
                .map(r ->{
                    r.setMensaje(datos.mensaje());
                    return new DatosDetalleRespuesta(r);
                }).orElseThrow(() -> new ValidacionException("Esa respuesta no existe"));
    }

    public void eliminar(Long id){
        if(!respuestaRepository.existsById(id)){
            throw new ValidacionException("La respuesta con ese id no existe");
        }
        respuestaRepository.deleteById(id);
    }
}
