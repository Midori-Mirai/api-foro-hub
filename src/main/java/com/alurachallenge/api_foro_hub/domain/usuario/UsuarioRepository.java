package com.alurachallenge.api_foro_hub.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
   Usuario findByEmail(String email);
}
