package com.bushido.controller;

import com.bushido.dto.request.CreatePerfilRequest;
import com.bushido.dto.request.UpdatePerfilRequest;
import com.bushido.dto.response.PerfilResponse;
import com.bushido.entity.Perfil;
import com.bushido.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/perfiles")
@Tag(name = "Perfiles", description = "Endpoints para operaciones CRUD sobre perfiles")
public class PerfilController {

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @Operation(summary = "Listar perfiles activos", description = "Devuelve la lista de perfiles activos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de perfiles obtenida con éxito")
    })
    @GetMapping
    public ResponseEntity<List<PerfilResponse>> listarPerfiles() {
        List<Perfil> perfiles = perfilService.listarPerfilesActivos();
        List<PerfilResponse> response = perfiles.stream()
                .map(p -> new PerfilResponse(p.getId(), p.getNombre()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Crear perfil", description = "Crea un nuevo perfil")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<PerfilResponse> crearPerfil(@RequestBody @Valid CreatePerfilRequest request) {
        Perfil perfil = new Perfil();
        perfil.setNombre(request.nombre());
        Perfil nuevoPerfil = perfilService.crearPerfil(perfil);
        PerfilResponse response = new PerfilResponse(nuevoPerfil.getId(), nuevoPerfil.getNombre());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Actualizar perfil", description = "Actualiza la información de un perfil existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PerfilResponse> actualizarPerfil(@PathVariable UUID id, @RequestBody @Valid UpdatePerfilRequest request) {
        Perfil perfil = new Perfil();
        perfil.setNombre(request.nombre());
        Perfil actualizado = perfilService.actualizarPerfil(id, perfil);
        PerfilResponse response = new PerfilResponse(actualizado.getId(), actualizado.getNombre());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar perfil", description = "Elimina lógicamente un perfil")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Perfil eliminado lógicamente"),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPerfil(@PathVariable UUID id) {
        perfilService.eliminarPerfil(id);
        return ResponseEntity.noContent().build();
    }
}
