-- V2__renombrar_tablas_a_plural.sql
ALTER TABLE curso RENAME TO cursos;
ALTER TABLE perfil RENAME TO perfiles;
ALTER TABLE respuesta RENAME TO respuestas;
ALTER TABLE topico RENAME TO topicos;
ALTER TABLE usuario RENAME TO usuarios;
ALTER TABLE usuario_perfil RENAME TO usuarios_perfiles;