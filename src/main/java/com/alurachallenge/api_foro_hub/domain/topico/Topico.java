package com.alurachallenge.api_foro_hub.domain.topico;

import com.alurachallenge.api_foro_hub.domain.curso.Curso;
import com.alurachallenge.api_foro_hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id/*Indica que esle id*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)/*Id incremental*/
    private Long id;

    @Setter
    private String titulo;

    @Setter
    private String mensaje;

    @Enumerated(EnumType.STRING)
    private StatusTopico status = StatusTopico.NO_RESPONDIDO;

    @Column(name = "fecha_creacion")
//    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    @Setter
    private Curso curso;


    public Topico(DatosRegistroTopico datos, Usuario autor, Curso curso) {
        this.id = null;
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.fechaCreacion = datos.fechaCreacion(); // <- ESTA LÍNEA DEBE ESTAR
        this.autor = autor;
        this.curso = curso;
        this.status = StatusTopico.NO_RESPONDIDO;
    }


}
