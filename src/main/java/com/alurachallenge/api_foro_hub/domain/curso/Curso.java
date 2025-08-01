package com.alurachallenge.api_foro_hub.domain.curso;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Curso")
@Table(name = "cursos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id") /*Identifica que dos objetos son iguales si tienen el mismo ID*/
public class Curso {
    @Id/*Indica que esle id*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)/*Id incremental*/
    private Long id;

    @Column(unique = true)
    private String nombre;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public Curso(DatosRegistroCurso datos) {
        this.id = null;
        this.nombre = datos.nombre();
        this.categoria = datos.categoria();
    }
}
