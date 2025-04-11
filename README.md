# BushidoChallenge

Este es el proyecto para el challenge técnico de Bushido Software. Aquí tenemos una API hecha con Java 21, Spring Boot 3, Gradle, JPA/Hibernate y PostgreSQL que se encarga de manejar usuarios, perfiles y permisos, y además consulta la cotización del dólar desde una API externa.

## ¿Qué hace la API?

- **Usuarios, Perfiles y Permisos:**
  - Puedes crear, actualizar y "eliminar" (lógicamente) usuarios, perfiles y permisos.
  - Un usuario puede tener varios perfiles y cada perfil puede tener varios permisos.
- **Borrado lógico:**
  - En vez de borrar registros, se marca que ya no están activos, lo que te permite conservar la info.
- **Cotización de divisas:**
  - Consulta la tasa de cambio del dólar a pesos (u otra combinación) usando exchangerate.host y guarda el resultado en la base de datos.
- **Documentación automática:**
  - Los endpoints están documentados con Swagger. Puedes probarlos en el navegador. `(http://localhost:8080/swagger-ui/index.html#/)`

## Requisitos para correrlo

- **Java 21** instalado (asegúrate de tenerlo).
- **Gradle** configurado en tu entorno.
- **PostgreSQL:**
  - Puedes usar una instalación local o Docker.
- **Editor de código:** (por ejemplo, IntelliJ IDEA)

## Cómo arrancarlo

1. **Clona el repo:**  
   Descárgate el código desde Git (o cópialo en una carpeta nueva).

2. **Configura la base de datos y la API key:**  
   Edita el archivo `src/main/resources/application.properties`

   - Pon la URL, usuario y contraseña de PostgreSQL.
   - Coloca tu API key para exchangerate.host en la propiedad `exchangerate.access_key` (por ejemplo: `18e23ba56d452c6324660ea9d7f9b710`).

3. **Compila y ejecuta el proyecto:**  
   Abre una terminal en la carpeta del proyecto y ejecuta:

   ```
   ./gradlew bootRun
   ```

   Esto compila la app y la arranca.

4. **Prueba la API:**
   - Abre tu navegador y ve a `http://localhost:8080` para ver la API en acción.
   - Para ver la documentación y probar los endpoints, entra a `http://localhost:8080/swagger-ui.html`.
# BushidoChallenge
