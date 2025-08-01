package com.alurachallenge.api_foro_hub.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    @Query("""
    SELECT t FROM Topico t
    WHERE t.curso.nombre = :nombreCurso
      AND YEAR(t.fechaCreacion) = :anio
    ORDER BY t.fechaCreacion ASC
""")
    List<Topico> findByCursoNombreAndAnio(@Param("nombreCurso") String nombreCurso, @Param("anio") int anio);
    /*@Param porque año no es un atributo y sino Spring JPA no lo identifica*/

    boolean existsByTituloAndMensajeAndIdNot(String titulo, String mensaje, Long id);
}
