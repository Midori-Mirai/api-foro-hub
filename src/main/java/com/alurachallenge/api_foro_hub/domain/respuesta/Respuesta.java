package com.alurachallenge.api_foro_hub.domain.respuesta;

import com.alurachallenge.api_foro_hub.domain.topico.Topico;
import com.alurachallenge.api_foro_hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "Respuesta")
@Table(name = "respuestas")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String mensaje;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    private Boolean solucion =  false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name  = "autor_id")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    public Respuesta(@Valid DatosRegistroRespuesta datos, Usuario autor, Topico topico) {
        this.id = null;
        this.mensaje = datos.mensaje();
        this.fechaCreacion =  LocalDateTime.now();
        this.solucion = false;
        this.autor = autor;
        this.topico = topico;
    }
}
