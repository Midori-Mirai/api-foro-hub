# API Foro Hub
![Portada del proyecto](imagenes/portada.png)

Este proyecto es una API REST desarrollada con **Spring Boot 3** para la gestión de un foro académico. Permite el registro y visualización de cursos, usuarios y tópicos de discusión, así como operaciones de búsqueda, actualización y eliminación de tópicos.

## 📌 Funcionalidades principales

### 📚 Cursos
- **Registrar curso**: permite dar de alta un nuevo curso en el sistema.
- **Listar cursos**: muestra todos los cursos registrados.

### 👤 Usuarios
- **Login usuario**: permite loguear a un usuario (email y contraseña) previamente registrado, usando un JWT.
- **Registrar usuario**: permite registrar un nuevo usuario en el sistema.
- **Mostrar todos los usuarios**: devuelve una lista con todos los usuarios registrados.
- **Buscar usuario por nombre**: permite filtrar los usuarios por fragmento de nombre.

### 💬 Tópicos
- **Registrar tópico**: crea un nuevo tópico de discusión asociado a un curso y un autor.
- **Mostrar todos los tópicos**: devuelve una lista con todos los tópicos registrados.
- **Mostrar tópico por ID**: devuelve los detalles de un tópico específico.
- **Buscar por curso y año**: permite filtrar los tópicos por nombre de curso y año de creación.
- **Actualizar tópico**: permite modificar el título, mensaje y curso de un tópico ya existente (sin alterar autor ni fecha de creación).
- **Eliminar tópico**: elimina permanentemente un tópico de la base de datos.

### 📥 Respuestas
- **Registrar respuesta**: agrega una nueva respuesta a un tópico específico, asociada a un autor.
- **Mostrar todas las respuestas**: devuelve una lista con todas las respuestas registradas.
- **Mostrar respuesta por ID**: devuelve los detalles de una respuesta específica.
- **Actualizar respuesta**: permite modificar el contenido de una respuesta existente (sin alterar el autor ni la fecha de creación).
- **Eliminar respuesta**: elimina permanentemente una respuesta de la base de datos.


## 🚀 Tecnologías utilizadas
- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL
- Postman / Insomnia (para pruebas)
- Lombok

## ⚙️ Requisitos previos
- JDK 21
- Maven
- MySQL Server
- IDE recomendado: IntelliJ IDEA

## ▶️ Ejecución del proyecto
1. Clona el repositorio.
2. Configura tu archivo `application.properties` con los datos de conexión a MySQL.
3. Ejecuta la clase principal para iniciar el servidor Spring Boot.
4. Prueba los endpoints usando Postman o Insomnia en `http://localhost:8080`.

## 📄 Estructura del proyecto
![Estructura del proyecto](imagenes/estructura%20del%20proyecto.png)
src/
 └── main/
     ├── java/
     │   └── com.alurachallenge.api_foro_hub/
     │       ├── configuration/
     │       │   └── CorsConfiguration
     │       ├── controller/
     │       │   ├── AutenticacionController
     │       │   ├── CursoController
     │       │   ├── RespuestaController
     │       │   ├── TopicoController
     │       │   └── UsuarioController
     │       ├── domain/
     │       │   ├── curso/
     │       │   ├── respuesta/
     │       │   ├── topico/
     │       │   ├── usuario/
     │       │   └── ValidacionException
     │       ├── infra/
     │       │   ├── exception/
     │       │   ├── security/
     │       │   └── springdoc/
     │       └── service/
     │           └── ApiForoHubApplication
     └── resources/
         ├── db.migration/
         ├── static/
         ├── templates/
         ├── application.properties
         └── ValidationMessages.properties



## 📬 Endpoints principales

| Método | URI                          | Descripción                                                                |
|--------|------------------------------|----------------------------------------------------------------------------|
| POST   | /cursos                      | Registrar nuevo curso                                                      |
| GET    | /cursos                      | Mostrar todos los cursos                                                   |
| POST   | /usuarios                    | Registrar nuevo usuario                                                    |
| GET    | /usuarios                    | Mostrar todos los usuarios                                                 |
| GET    | /usuarios/buscar             | Buscar usuario por fragmento de nombre                                     |
| POST   | /topicos                     | Registrar nuevo tópico                                                     |
| GET    | /topicos                     | Listar todos los tópicos                                                   |
| GET    | /topicos/{id}                | Mostrar detalles de un tópico por ID                                       |
| GET    | /topicos/buscar              | Buscar tópico por nombre de curso y año (`?curso=Spring boot 3&anio=2025`) |
| PUT    | /topicos/{id}                | Actualizar un tópico existente                                             |
| DELETE | /topicos/{id}                | Eliminar un tópico                                                         |
| POST   | /respuestas                  | Registrar nueva respuesta                                                  |
| GET    | /respuestas                  | Listar todas las respuestas                                                |
| GET    | /respuestas/{id}             | Mostrar detalles de una respuesta por ID                                   |
| PUT    | /respuestas/{id}             | Actualizar un respuesta                                                    |
| DELETE | /respuestas/{id}             | Eliminar un respuestas                                                     |

## 🔐 Seguridad con JWT

El proyecto utiliza **JWT (JSON Web Token)** para asegurar el acceso a los endpoints. A continuación, se describe el proceso de implementación:

### 1. Login sin token
El endpoint `POST /login` permite a los usuarios autenticarse enviando su email y contraseña. Si las credenciales son válidas, se genera un token JWT.

### 2. Generación de token
Se creó la clase `TokenService` que utiliza la librería de JWT para generar un token firmado con una clave secreta. Este token incluye el email del usuario como "subject" y una fecha de expiración.

### 3. Filtro de seguridad
Se implementó la clase `SecurityFilter`, extendiendo de `OncePerRequestFilter`. Este filtro se ejecuta en cada petición y:
- Extrae el token del encabezado `Authorization`.
- Valida y decodifica el token.
- Carga al usuario desde la base de datos.
- Autentica al usuario en el contexto de seguridad de Spring.

### 4. Configuración de seguridad
En la clase `SecurityConfigurations`:
- Se deshabilita CSRF.
- Se configura la política de sesión como stateless.
- Se permite acceso libre solo al endpoint `/login`.
- Se requiere autenticación para todos los demás endpoints.
- Se registra el `SecurityFilter` antes del filtro de autenticación por defecto de Spring.

### 5. Clases involucradas
- `DatosAutenticacionUsuario`: DTO para login.
- `AutenticacionController`: controlador para `/login`.
- `TokenService`: genera y valida tokens.
- `SecurityFilter`: filtra y autentica solicitudes con token.
- `SecurityConfigurations`: configuración general de seguridad.

### 6. Pruebas con Insomnia o Postman
1. Realiza una petición `POST /login` con email y contraseña válidos.
2. Copia el token devuelto.
3. Usa ese token como `Bearer Token` en las solicitudes protegidas (`/topicos`, `/cursos`, etc.).

## 🛡️ Validaciones
- Todos los campos requeridos son validados.
- No se permiten títulos y mensajes duplicados.
- Se valida la existencia de registros antes de realizar acciones sobre ellos.

## 📫 Autor
| [<img src="https://avatars.githubusercontent.com/u/196402413?v=4" width=115><br><sub>Desarrollado por: Zaricell Bautista</sub>](https://github.com/Midori-Mirai) |  
| :---: | :---: | :---: |
. Proyecto con fines educativos como parte del curso de Alura Latam: *Spring Boot 3: Practicando Spring Framework: Challenge Foro Hub*.

