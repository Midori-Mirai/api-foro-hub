package com.alurachallenge.api_foro_hub.domain.usuario;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {
    @Id/*Indica que esle id*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)/*Id incremental*/
    private Long id;
    private String nombre;
    private String email;

    @Setter
    private String contrasena;
}
