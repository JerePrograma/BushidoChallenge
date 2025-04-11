package com.bushido.controller;

import com.bushido.dto.request.CreateUsuarioRequest;
import com.bushido.dto.request.UpdateUsuarioRequest;
import com.bushido.dto.response.PerfilResponse;
import com.bushido.dto.response.UsuarioResponse;
import com.bushido.dto.response.UsuarioListadoResponse;
import com.bushido.entity.Usuario;
import com.bushido.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Endpoints para operaciones CRUD sobre usuarios y asignación de perfiles")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Listar usuarios activos", description = "Devuelve la lista de usuarios activos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida con éxito")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioListadoResponse>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuariosActivos();
        List<UsuarioListadoResponse> response = usuarios.stream()
                .map(u -> new UsuarioListadoResponse(
                        u.getId(),
                        u.getNombre(),
                        u.getEmail(),
                        u.getPerfiles().stream()
                                .map(p -> new PerfilResponse(p.getId(), p.getNombre()))
                                .collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Crear usuario", description = "Crea un nuevo usuario en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<UsuarioResponse> crearUsuario(@RequestBody @Valid CreateUsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNombre(request.nombre());
        usuario.setEmail(request.email());
        Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
        UsuarioResponse response = new UsuarioResponse(nuevoUsuario.getId(), nuevoUsuario.getNombre(), nuevoUsuario.getEmail());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza la información de un usuario existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(@PathVariable UUID id, @RequestBody @Valid UpdateUsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNombre(request.nombre());
        usuario.setEmail(request.email());
        Usuario actualizado = usuarioService.actualizarUsuario(id, usuario);
        UsuarioResponse response = new UsuarioResponse(actualizado.getId(), actualizado.getNombre(), actualizado.getEmail());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina lógicamente un usuario (borrado lógico)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado lógicamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable UUID id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Asignar perfiles", description = "Asigna uno o más perfiles a un usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfiles asignados correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario o perfil no encontrado")
    })
    @PostMapping("/{id}/perfiles")
    public ResponseEntity<UsuarioResponse> asignarPerfiles(@PathVariable UUID id, @RequestBody Set<UUID> perfilIds) {
        Usuario usuario = usuarioService.asignarPerfiles(id, perfilIds);
        UsuarioResponse response = new UsuarioResponse(usuario.getId(), usuario.getNombre(), usuario.getEmail());
        return ResponseEntity.ok(response);
    }
}
